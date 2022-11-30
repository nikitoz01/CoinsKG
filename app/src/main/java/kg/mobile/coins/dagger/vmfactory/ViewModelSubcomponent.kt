package kg.mobile.coins.dagger.vmfactory;

import dagger.Subcomponent;
import kg.mobile.coins.ui.fragment.MainFragment;
import kg.mobile.coins.ui.fragment.categorycoin.category.CategoryFragment;
import kg.mobile.coins.ui.fragment.categorycoin.coin.CoinFragment;
import kg.mobile.coins.ui.fragment.coindetail.CoinDetailFragment;
import kg.mobile.coins.ui.fragment.coinsearch.CoinSearchFragment;

@FragmentScope
@Subcomponent(modules = [ViewModelModule::class])
interface ViewModelSubcomponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): ViewModelSubcomponent
    }

    fun inject(mainFragment: MainFragment)

    fun inject(categoryFragment: CategoryFragment)

    fun inject(coinFragment: CoinFragment)

    fun inject(coinDetailFragment: CoinDetailFragment)

    fun inject(coinSearchFragment: CoinSearchFragment)

}
