package kg.mobile.coins.dagger


import android.content.Context
import dagger.Component
import kg.mobile.coins.dagger.glide.GlideDaggerModule
import kg.mobile.coins.dagger.vmfactory.ViewModelSubcomponent
import javax.inject.Singleton

interface AppComponentDependencies {
    val context: Context
}

@Component(
    dependencies = [AppComponentDependencies::class],
    modules = [AppModule::class, RetrofitModule::class, RoomModule::class, GlideDaggerModule::class, ]
)
@Singleton
interface AppComponent {

    @Component.Builder
    interface Builder {
        fun build(): AppComponent

        fun bindContext(appComponentDependencies: AppComponentDependencies): Builder

    }

    fun viewModelComponent(): ViewModelSubcomponent.Factory
}