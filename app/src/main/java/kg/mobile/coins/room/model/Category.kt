package kg.mobile.coins.room.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity(tableName = "category")
data class Category(
    @PrimaryKey
    val id: Int,
    @NotNull
    var name: String,
    var parentId: Int?,
    var period: String?,
    var description: String?,
    var detailURL: String?,
    var updateTime: Long,
    var isActive: Boolean
)
