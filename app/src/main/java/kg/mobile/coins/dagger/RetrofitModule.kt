package kg.mobile.coins.dagger

import dagger.Module
import dagger.Provides
import kg.mobile.coins.dagger.source.GlideDataSource
import kg.mobile.coins.repository.ApiRepository
import kg.mobile.coins.retrofit.CoinsRestApi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Scope
import javax.inject.Singleton


@Module
class RetrofitModule {
    @Provides
    @Singleton
    fun provideOkhttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
//            .connectTimeout(20, TimeUnit.SECONDS)
//            .readTimeout(20, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofitClient(apiUrl: String, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(apiUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideRestApi(retrofit: Retrofit): CoinsRestApi {
        return retrofit.create(CoinsRestApi::class.java)
    }

    @Provides
    @Singleton
    fun provideApiUrl() = "http://192.168.1.103:8081"

    @Singleton
    @Provides
    fun restRepositoryProvide(restApi: CoinsRestApi, glideRepository: GlideDataSource) =
        ApiRepository(restApi, glideRepository)
}

@Scope
@Retention(AnnotationRetention.RUNTIME)
public annotation class RetrofitScope {}