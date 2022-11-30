package kg.mobile.coins.dagger

import android.content.Context
import dagger.Module
import dagger.Provides
import kg.mobile.coins.repository.GlideRepository
import javax.inject.Singleton


@Module
class GlideDaggerModule {
    @Singleton
    @Provides
    fun provideGlideRepository(context: Context, apiURL: String) = GlideRepository(context,apiURL)

}
