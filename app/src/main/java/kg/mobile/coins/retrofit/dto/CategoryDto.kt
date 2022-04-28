package kg.mobile.coins.retrofit.dto

data class CategoryDto(
    val id: Int,
    var name: String,
    var parentId: Int?,
    var period: String?,
    var description: String?,
    var detailURL: String?,
    var updateTime: Long,
    var isActive: Boolean
)
