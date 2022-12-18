package kg.mobile.coins.ui.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
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

class MainFragment : Fragment(R.layout.fragment_main) {

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
        super.onCreate(savedInstanceState)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        requireContext().appComponent.viewModelComponent().create().inject(this)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putBoolean("isSearchActive", isSearchActive)
        super.onSaveInstanceState(outState)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        isSearchActive = savedInstanceState?.getBoolean("isSearchActive") ?: false
        super.onViewStateRestored(savedInstanceState)
    }


//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        _mainBinding = FragmentMainBinding.inflate(inflater, container, false)
//        return mainBinding.root
//    }

    override fun onDestroyView() {
        _mainBinding = null
        super.onDestroyView()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _mainBinding = FragmentMainBinding.bind(view)

        defaultNavHost =
            (childFragmentManager.findFragmentById(R.id.defaultFragmentContainerView) as NavHostFragment)

        val backCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (defaultNavHost.childFragmentManager.backStackEntryCount > 0) {
                    defaultNavHost.findNavController().popBackStack()
                    //  if(isSearchActive) defaultNavHost.findNavController().popBackStack()
                    isSearchActive = false
                } else {
                    requireActivity().finish()
                }
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, backCallback)

        val alreadyUpdated: Boolean = arguments?.let {
            MainFragmentArgs.fromBundle(it).alreadyUpdated
        }?: false

        mainViewModel.isAnyCoinsExists()

        mainBinding.swipeRefreshLayout.setOnRefreshListener {
            loadNewData()
        }

        mainViewModel.existsCheck.observe(viewLifecycleOwner) {
            if (it) {
                (requireActivity() as AppCompatActivity).supportActionBar?.show()
                mainBinding.defaultFragmentContainerView.visibility = View.VISIBLE
            } else {
                mainBinding.loadState.root.visibility = View.VISIBLE
            }
        }

        (requireActivity() as MenuHost).apply {

            addMenuProvider(object : MenuProvider {

                override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                }

                override fun onMenuItemSelected(item: MenuItem): Boolean {
                    return when (item.itemId) {
                        R.id.app_bar_search -> {
                            if (!isSearchActive) {
                                isSearchActive = true
                                defaultNavHost.findNavController().navigate(
                                    CategoryCoinFragmentDirections.actionCategoryCoinFragmentToCoinSearchFragment()
                                )
                            }
                            true
                        }
                        R.id.app_bar_info -> {
                            defaultNavHost.findNavController().navigate(
                                CategoryCoinFragmentDirections.actionGlobalAboutApp()
                            )
                            true
                        }
                        else -> false
                    }
                }

            }, viewLifecycleOwner, Lifecycle.State.CREATED)
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
                       }
                        }
                    }

                launch {
                    mainViewModel.coinStateFlow.collect { viewState ->
                        when (viewState) {
                            is State.Fail -> {
                                mainBinding.swipeRefreshLayout.isRefreshing = false
                                Log.e("NewCoins", "FAILLLLL ${viewState.exception}")
                                Toast.makeText(
                                    context,
                                    "Ошибка загрузки, повторите попытку позже",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                            is State.Success -> {
                                mainViewModel.insertCoins(viewState.value)
                                checkLoad()
                            }
                            is State.Loading -> {
                                Log.d("NewsCoins", "LOADINGGG ")
                            }
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
                    (this.coinStateFlow.value as State.Success).value.isNotEmpty()
                ) {
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