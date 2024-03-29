package kg.mobile.coins.ui.fragment.categorycoin.coin

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kg.mobile.coins.R
import kg.mobile.coins.appComponent
import kg.mobile.coins.dagger.vmfactory.MultiViewModelFactory
import kg.mobile.coins.databinding.FragmentCoinBinding
import kg.mobile.coins.ui.fragment.categorycoin.CategoryCoinFragmentDirections
import kg.mobile.coins.ui.fragment.categorycoin.CategoryCoinViewModel
import kg.mobile.coins.util.safeNavigate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class CoinFragment : Fragment(R.layout.fragment_coin) {

    private var _coinBinding: FragmentCoinBinding? = null
    private val coinBinding
        get() = _coinBinding!!

    @Inject
    lateinit var factory: MultiViewModelFactory

    private val coinViewModel: CoinViewModel by viewModels { factory }

    private val parentViewModel: CategoryCoinViewModel by viewModels({ requireParentFragment() })

    private lateinit var adapter: CoinAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        requireContext().appComponent.viewModelComponent().create().inject(this)
    }




    override fun onDestroyView() {
        _coinBinding = null
        super.onDestroyView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _coinBinding = FragmentCoinBinding.bind(view)

        val recyclerview = coinBinding.coinRecyclerView
        recyclerview.layoutManager = LinearLayoutManager(activity)
        adapter = CoinAdapter({ coin ->
            parentFragment?.let {
                findNavController()
                    .safeNavigate(
                        CategoryCoinFragmentDirections.actionCategoryCoinFragmentToCoinDetailFragment(
                            coin.id
                        )
                    )
            }
        },
            { coin ->
                coinViewModel.updateCoin(coin)
            }
        )
        recyclerview.adapter = adapter


        parentViewModel.getParentId().observe(viewLifecycleOwner) { parent ->
            coinViewModel.getCoins(parent)

            lifecycleScope.launch(Dispatchers.IO) {
                coinViewModel.coinFlow!!.collectLatest {
                    adapter.submitData(it)
                }
            }

        }
    }

    companion object {
        fun newInstance(categoryId: Int?): CoinFragment {
            val bundle = Bundle()
            bundle.putString("categoryId", categoryId?.toString() ?: "")
            val fragment = CoinFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}