package kg.mobile.coins.ui.fragment.coinsearch

import android.content.Context
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kg.mobile.coins.R
import kg.mobile.coins.appComponent
import kg.mobile.coins.dagger.vmfactory.MultiViewModelFactory
import kg.mobile.coins.databinding.FragmentCoinSearchBinding
import kg.mobile.coins.ui.fragment.categorycoin.coin.CoinAdapter
import kg.mobile.coins.util.safeNavigate
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


class CoinSearchFragment : Fragment(R.layout.fragment_coin_search) {

    private var _coinSearchBinding: FragmentCoinSearchBinding? = null
    private val coinSearchBinding
        get() = _coinSearchBinding!!

    @Inject
    lateinit var factory: MultiViewModelFactory

    private lateinit var adapter: CoinAdapter

    private val coinSearchViewModel: CoinSearchViewModel by viewModels {
        factory
    }

    private var currentMode = 0
    private var currentQuery: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        requireContext().appComponent.viewModelComponent().create().inject(this)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.findItem(R.id.app_bar_info).isVisible = false

        menu.findItem(R.id.app_bar_search).apply {
            (actionView as androidx.appcompat.widget.SearchView).apply {
                setOnQueryTextListener(object :
                    androidx.appcompat.widget.SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        hideKeyboard()
                        println("submit")
                        return true
                    }

                    override fun onQueryTextChange(newText: String?): Boolean {
                        currentQuery = newText
                        coinSearchViewModel.setSearchBy(newText + "", currentMode)
                        return true
                    }
                })
//            setOnSearchClickListener {
//                println("search")
//            }

            }
            setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
                override fun onMenuItemActionExpand(item: MenuItem?): Boolean {
                    return true
                }

                override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
                    hideKeyboard()
                    activity?.onBackPressed()
                    return true
                }
            })
        }
        super.onCreateOptionsMenu(menu, inflater)
    }


    private fun hideKeyboard() {
        val inputMethodManager =
            context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view?.windowToken, 0)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _coinSearchBinding = FragmentCoinSearchBinding.inflate(inflater, container, false)
        val recyclerview = coinSearchBinding.coinSearchRecyclerView
        recyclerview.layoutManager = LinearLayoutManager(activity)
        adapter = CoinAdapter({ coin ->
            findNavController().safeNavigate(
                CoinSearchFragmentDirections.actionCoinSearchFragmentToCoinDetailFragment(coin.id)
            )
        },
            { coin -> coinSearchViewModel.updateCoin(coin) }
        )

        recyclerview.adapter = adapter


        return coinSearchBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        (activity as AppCompatActivity).supportActionBar?.title = "Поиск"
        coinSearchBinding.chipGroup.setOnCheckedStateChangeListener { _, i ->
            if (i.isNotEmpty()) when (i[0]) {
                coinSearchBinding.firstChip.id -> currentMode = 0
                coinSearchBinding.secondChip.id -> currentMode = 1
                coinSearchBinding.thirdChip.id -> currentMode = 2
            } else coinSearchBinding.chipGroup.check(coinSearchBinding.firstChip.id)
            coinSearchViewModel.setSearchBy(currentQuery + "", currentMode)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            coinSearchViewModel.coinSearchFlow.collectLatest { pagingData ->
                adapter.submitData(pagingData)
            }
        }
    }

    override fun onDestroyView() {
        _coinSearchBinding = null
        super.onDestroyView()
    }

}
