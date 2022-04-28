package kg.mobile.coins.dagger.vmfactory

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import kg.mobile.coins.ui.fragment.MainFragmentViewModel
import kg.mobile.coins.ui.fragment.category.CategoryViewModel
import kg.mobile.coins.ui.fragment.coin.CoinViewModel
import kg.mobile.coins.ui.fragment.coindetail.CoinDetailViewModel

@Module
interface ViewModelModule {
    @Binds
    @[IntoMap ViewModelKey(MainFragmentViewModel::class) ]
    fun bindMainFragmentViewModel(mainFragmentViewModel: MainFragmentViewModel): ViewModel

    @Binds
    @[IntoMap ViewModelKey(CategoryViewModel::class) ]
    fun bindCategoryViewModel(categoryViewModel: CategoryViewModel): ViewModel

    @Binds
    @[IntoMap ViewModelKey(CoinViewModel::class) ]
    fun bindCoinViewModel(coinViewModel: CoinViewModel): ViewModel

    @Binds
    @[IntoMap ViewModelKey(CoinDetailViewModel::class) ]
    fun bindCoinDetailViewModel(coinDetailViewModel: CoinDetailViewModel): ViewModel
}