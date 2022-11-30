package kg.mobile.coins.dagger.glide

import android.content.Context
import dagger.Module
import dagger.Provides
import kg.mobile.coins.repository.GlideRepository


@Module
class GlideDaggerModule {

    @Provides
    fun provideGlideRepository(context: Context, apiURL: String) = GlideRepository(context, apiURL)

}
