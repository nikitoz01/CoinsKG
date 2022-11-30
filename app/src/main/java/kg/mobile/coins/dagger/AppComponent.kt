package kg.mobile.coins.dagger


import android.content.Context
import dagger.Component
import kg.mobile.coins.dagger.vmfactory.ViewModelModule
import kg.mobile.coins.ui.fragment.MainFragment
import kg.mobile.coins.ui.fragment.categorycoin.category.CategoryFragment
import kg.mobile.coins.ui.fragment.categorycoin.coin.CoinFragment
import kg.mobile.coins.ui.fragment.coindetail.CoinDetailFragment
import kg.mobile.coins.ui.fragment.coinsearch.CoinSearchFragment
import javax.inject.Singleton

interface AppComponentDependencies {
   val context: Context
}

@Component(dependencies = [AppComponentDependencies::class],
    modules = [AppModule::class,ViewModelModule::class,
        RetrofitModule::class, RoomModule::class,GlideDaggerModule::class])
@Singleton
interface AppComponent {

    fun inject(mainFragment: MainFragment)
    fun inject(categoryFragment: CategoryFragment)
    fun inject(coinFragment: CoinFragment)
    fun inject(coinDetailFragment: CoinDetailFragment)
    fun inject(coinSearchFragment: CoinSearchFragment)

    @Component.Builder
    interface Builder {
        fun build(): AppComponent

        fun bindContext(appComponentDependencies: AppComponentDependencies): Builder

    }
}