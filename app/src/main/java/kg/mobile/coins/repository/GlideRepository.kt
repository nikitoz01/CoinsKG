package kg.mobile.coins.repository

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kg.mobile.coins.room.model.Coin
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.File
import javax.inject.Inject


class GlideRepository @Inject constructor(private val context: Context, apiURL: String) {
    private val glideRestApiUrl = "$apiURL/images?imageName="

    fun loadImage(coinsWithoutImage: List<Coin>): Flow<List<Coin>> = flow {
        coinsWithoutImage.apply {
            forEach {
                Glide.with(context)
                    .download(glideRestApiUrl + it.imageUrl)
                    .diskCacheStrategy(DiskCacheStrategy.DATA)
                    .addListener(object : RequestListener<File> {
                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: Target<File>?,
                            isFirstResource: Boolean
                        ): Boolean {
                            return false
                        }

                        override fun onResourceReady(
                            resource: File?,
                            model: Any?,
                            target: Target<File>?,
                            dataSource: DataSource?,
                            isFirstResource: Boolean
                        ): Boolean {
                            it.isImageLoaded = true
                            it.imagePath = resource?.absolutePath
                            return false
                        }
                    })
                    .submit().get()
                //println(Thread.currentThread())
            }
            emit(this)
        }
    }
}