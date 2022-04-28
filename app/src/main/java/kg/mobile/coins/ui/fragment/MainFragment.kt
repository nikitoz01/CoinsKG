package kg.mobile.coins.ui.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
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
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainFragment: Fragment(R.layout.fragment_main) {

    private var _mainBinding: FragmentMainBinding?=null

    val mainBinding
    get() = _mainBinding!!

    @Inject
    lateinit var viewModelFactory: MultiViewModelFactory


    private val mainViewModel: MainFragmentViewModel by viewModels {
        viewModelFactory
    }

    override fun onAttach(context: Context) {
        context.appComponent.inject(this)
        super.onAttach(context)
        val backCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (childFragmentManager.backStackEntryCount > 0) {
                    childFragmentManager.popBackStack()
                    childFragmentManager.findFragmentById(R.id.coinFragmentContainerView)?.let{
                        (it as NavHostFragment).findNavController().popBackStack()
                    }
                } else {
                    parentFragmentManager.popBackStack()
                    requireActivity().finish()
                }
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, backCallback)
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
        mainBinding.swipeRefreshLayout.setOnRefreshListener {
            mainViewModel.retryLoad()
        }
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    mainViewModel.categoryStateFlow.collect { viewState ->
                        when (viewState) {
                            is State.Fail -> {
                                mainViewModel.cancelLoad()
                                mainBinding.swipeRefreshLayout.isRefreshing=false
                                Log.e("NewCategories", " FAILLLLL ${viewState.exception}")
                            }
                            is State.Success -> {
                                mainViewModel.insertCategories(viewState.value)
                                mainViewModel.apply { updateSharedPref("categoryUpdateTime", viewState.value.maxByOrNull {it.updateTime}?.updateTime ?: return@apply )}
                                checkLoad()
                            }
                            is State.Loading -> {
                                Log.d("NewCategories", " LOADINGGG ") }
                        }
                    }
                }
                launch {
                    mainViewModel.coinStateFlow.collect { viewState ->
                       when (viewState) {
                           is State.Fail ->{
                               mainViewModel.cancelLoad()
                               mainBinding.swipeRefreshLayout.isRefreshing=false
                               Log.e("NewCoins", "FAILLLLL ${viewState.exception}" )
                           }
                           is State.Success ->{
                               mainViewModel.insertCoins(viewState.value)
                               mainViewModel.apply { updateSharedPref("coinUpdateTime", viewState.value.maxByOrNull {it.updateTime}?.updateTime ?: return@apply )}
                               checkLoad()
                           }
                           is State.Loading->{
                               Log.d("NewsCoins", "LOADINGGG " )
                           }
                       }
                    }
                }
            }
        }
    }

    private fun checkLoad() {
        if(with(mainViewModel){
                coinStateFlow.value is State.Success &&
                        categoryStateFlow.value is State.Success
            }) mainBinding.swipeRefreshLayout.isRefreshing = false
    }

}