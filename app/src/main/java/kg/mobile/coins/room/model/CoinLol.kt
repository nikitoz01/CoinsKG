package kg.mobile.coins.room.model


import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CoinLol(
    val id:Int,
    var categoryId: Int?,
    var name: String,
    var zenoId: Int?,
    var imageUrl: String,
    var rarity: String?,
    var estimate: String?,
    var auctionURL: String?,
    var year: String?,
    var size: String?,
    var weight: String?,
    var metal: String?,
    var mint: String?,
    var denomination: String?,
    var description: String?,
    var descriptionDetailURL:String?,
    var updateTime: Long,
    var isActive: Boolean,
    var isImageLoaded: Boolean = false,
    var imagePath: String? = ""
): Parcelable
