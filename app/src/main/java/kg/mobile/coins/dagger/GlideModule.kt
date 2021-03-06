package kg.mobile.coins.dagger

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule
import dagger.Module
import dagger.Provides
import kg.mobile.coins.repository.GlideRepository
import kg.mobile.coins.repository.RoomRepository
import javax.inject.Singleton


@Module
class GlideDaggerModule {
    @Singleton
    @Provides
    fun provideGlideRepository(context: Context, roomRepository: RoomRepository) = GlideRepository(context,roomRepository)
}

@GlideModule
class GlideAppModule : AppGlideModule() {

    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
        super.registerComponents(context, glide, registry)
    }

}