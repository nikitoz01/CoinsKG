package kg.mobile.coins.dagger

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import kg.mobile.coins.dagger.vmfactory.ViewModelSubcomponent


@Module(subcomponents = [ViewModelSubcomponent::class])
class AppModule {

    @Provides
    fun provideSharedPreferences(context: Context): SharedPreferences =
        context.getSharedPreferences("updateTime", Context.MODE_PRIVATE)

}