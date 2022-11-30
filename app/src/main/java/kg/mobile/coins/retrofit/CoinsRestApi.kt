package kg.mobile.coins.retrofit

import kg.mobile.coins.retrofit.dto.CategoryDto
import kg.mobile.coins.retrofit.dto.CoinDto
import kg.mobile.coins.retrofit.dto.ImageDto
import retrofit2.http.GET
import retrofit2.http.Query


interface CoinsRestApi {
    @GET("/categories")
    suspend fun getCategories(): List<CategoryDto>

    @GET("/categories")
    suspend fun getCategories(@Query("updateTime") updateTime: Long): List<CategoryDto>

    @GET("/categories?mode=main")
    suspend fun getMainCategories(): List<CategoryDto>

    @GET("/coins")
    suspend fun getCoins(): List<CoinDto>

    @GET("/coins")
    suspend fun getCoins(@Query("updateTime") updateTime: Long): List<CoinDto>

    @GET("/coins?mode=main")
    suspend fun getMainCoins(): List<CoinDto>

    @GET("/coins")
    suspend fun getCoinsByCategoryID(): List<CoinDto>


    @GET("/images")
    suspend fun getImages(): List<ImageDto>

    @GET("/images")
    suspend fun getImages(@Query("updateTime") updateTime: Long): List<ImageDto>

    @GET("/images")
    suspend fun getImage(@Query("imageName") imageName: String): ImageDto
}