package kg.mobile.coins.ui.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import kg.mobile.coins.R
import kg.mobile.coins.appComponent
import kg.mobile.coins.dagger.vmfactory.MultiViewModelFactory
import kg.mobile.coins.databinding.FragmentMainBinding
import kg.mobile.coins.model.State
import kg.mobile.coins.ui.fragment.categorycoin.CategoryCoinFragmentDirections
import kg.mobile.coins.ui.fragment.categorycoin.coin.CoinFragment
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainFragment: Fragment(R.layout.fragment_main) {

    private var _mainBinding: FragmentMainBinding? = null

    val mainBinding
        get() = _mainBinding!!

    @Inject
    lateinit var viewModelFactory: MultiViewModelFactory

    lateinit var defaultNavHost: NavHostFragment

    private val mainViewModel: MainFragmentViewModel by viewModels {
        viewModelFactory
    }

    private var isSearchActive = false

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    override fun onAttach(context: Context) {
        context.appComponent.inject(this)
        super.onAttach(context)
        val backCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (defaultNavHost.childFragmentManager.backStackEntryCount > 0) {
                        defaultNavHost.findNavController().popBackStack()
                  //  if(isSearchActive) defaultNavHost.findNavController().popBackStack()
                        isSearchActive = false
                    }
                else {
                    parentFragmentManager.popBackStack()
                    requireActivity().finish()
                }
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, backCallback)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
            when (item.itemId) {
                R.id.app_bar_search -> {
                    if(!isSearchActive) {
                        isSearchActive = true
                        defaultNavHost.findNavController().navigate(
                            CategoryCoinFragmentDirections.actionCategoryCoinFragmentToCoinSearchFragment()
                        )
                    }
                    return true
                }
            }
        when (item.itemId) {
            R.id.app_bar_info -> {
                defaultNavHost.findNavController().navigate(
                        CategoryCoinFragmentDirections.actionGlobalAboutApp()
                )
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _mainBinding = FragmentMainBinding.inflate(inflater, container, false)
        return mainBinding.root
    }

    override fun onDestroyView() {
        _mainBinding = null
        super.onDestroyView()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        defaultNavHost = (childFragmentManager.
        findFragmentById(R.id.defaultFragmentContainerView) as NavHostFragment)

        val alreadyUpdated: Boolean = arguments?.let {
            MainFragmentArgs.fromBundle(it).alreadyUpdated
        }!!

        mainViewModel.isAnyCoinsExists()

        mainBinding.swipeRefreshLayout.setOnRefreshListener {
            loadNewData()
        }

        mainViewModel.existsCheck.observe(viewLifecycleOwner){
            if (it) {
                (activity as AppCompatActivity).supportActionBar?.show()
                mainBinding.mainScrollView.visibility = View.VISIBLE
            }
            else {
                mainBinding.loadStateFrameLayout.visibility = View.VISIBLE
            }
        }

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    mainViewModel.categoryStateFlow.collect { viewState ->
                        when (viewState) {
                            is State.Fail -> {
                                // mainViewModel.cancelLoad()
                                mainBinding.swipeRefreshLayout.isRefreshing = false
                                Log.e("NewCategories", " FAILLLLL ${viewState.exception}")
                            }
                            is State.Success -> {
                                mainViewModel.insertCategories(viewState.value)

                                checkLoad()
                            }
                            is State.Loading -> {
                                Log.d("NewCategories", " LOADINGGG ")
                            }
                            else -> {}
                        }
                    }
                }
                launch {
                    mainViewModel.coinStateFlow.collect { viewState ->
                        when (viewState) {
                            is State.Fail -> {
                                //  mainViewModel.cancelLoad()
                                mainBinding.swipeRefreshLayout.isRefreshing = false
                                Log.e("NewCoins", "FAILLLLL ${viewState.exception}")
                                Toast.makeText(context,"???????????? ????????????????, ?????????????????? ?????????????? ??????????",Toast.LENGTH_LONG).show()
                            }
                            is State.Success -> {
                                mainViewModel.insertCoins(viewState.value)
                                checkLoad()
                            }
                            is State.Loading -> {
                                Log.d("NewsCoins", "LOADINGGG ")
                            }
                            else -> {}
                        }
                    }
                }
            }
        }
        if (!alreadyUpdated) loadNewData()
    }

    private fun loadNewData() {
        mainViewModel.loadNewData()
        mainBinding.swipeRefreshLayout.isRefreshing = true
    }

    private fun checkLoad() {
        mainViewModel.apply {
            if (with(this) {
                    coinStateFlow.value is State.Success &&
                            categoryStateFlow.value is State.Success
                }) {
                mainBinding.swipeRefreshLayout.isRefreshing = false
                if ((this.categoryStateFlow.value as State.Success).value.isNotEmpty() ||
                    (this.coinStateFlow.value as State.Success).value.isNotEmpty()){
                    findNavController().apply {
                        navigate(MainFragmentDirections.actionGlobalMainFragment(true))
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    companion object {
        fun newInstance(alreadyUpdated: Boolean? = false): CoinFragment {
            val bundle = Bundle()
            bundle.putString("alreadyUpdated", alreadyUpdated.toString())
            val fragment = CoinFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}