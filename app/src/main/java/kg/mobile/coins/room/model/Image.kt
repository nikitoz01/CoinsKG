package kg.mobile.coins.room.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "image")
data class Image(
    @PrimaryKey
    val id:Int,
    var url: String,
    var updateTime: Long
)