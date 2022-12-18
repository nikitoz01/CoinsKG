package kg.mobile.coins.room.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "category")
data class Category(
    @PrimaryKey
    val id: Int,
    var name: String,
    var parentId: Int?,
    var period: String?,
    var description: String?,
    var detailURL: String?,
    var updateTime: Long,
    var isActive: Boolean
)
