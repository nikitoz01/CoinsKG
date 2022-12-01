package kg.mobile.coins.dagger.glide

import android.content.Context
import dagger.Module
import dagger.Provides
import kg.mobile.coins.dagger.source.GlideDataSource


@Module
class GlideDaggerModule {

    @Provides
    fun provideGlideDataSource(context: Context, apiURL: String) = GlideDataSource(context, apiURL)

}
