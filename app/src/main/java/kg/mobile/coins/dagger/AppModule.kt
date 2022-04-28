package kg.mobile.coins.dagger

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import kg.mobile.coins.repository.RestRepositoryImpl
import kg.mobile.coins.retrofit.CoinsRestApi
import javax.inject.Singleton


@Module
class AppModule {
    @Singleton
    @Provides
    fun restRepositoryProvide(restApi: CoinsRestApi) = RestRepositoryImpl(restApi)

    @Provides
    fun provideSharedPreferences(context: Context): SharedPreferences = context.getSharedPreferences("updateTime", Context.MODE_PRIVATE)

}