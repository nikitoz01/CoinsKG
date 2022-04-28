package kg.mobile.coins.repository

import kg.mobile.coins.retrofit.dto.CategoryDto
import kg.mobile.coins.retrofit.dto.CoinDto
import kg.mobile.coins.retrofit.dto.ImageDto
import kg.mobile.coins.room.dao.CategoryDao
import kg.mobile.coins.room.dao.CoinDao
import kg.mobile.coins.room.dao.ImageDao
import kg.mobile.coins.room.model.Coin
import kg.mobile.coins.util.categoryDtoToModel
import kg.mobile.coins.util.coinDtoToModel
import kg.mobile.coins.util.imageDtoToModel
import javax.inject.Inject

class RoomRepositoryImpl @Inject constructor(
    private val categoryDao: CategoryDao,
    private val coinDao: CoinDao,
    private val imageDao: ImageDao) {

    fun getAllCategories() = categoryDao.getAllCategories()

    fun getAllCoins() = coinDao.getAllCoins()

    fun getAllCoinsWithoutImage() = coinDao.getAllCoinsWithoutImage()

    suspend fun insertAllCategories(categories: List<CategoryDto>) = categoryDao.insertAll(categories.map {it.categoryDtoToModel() })

    suspend fun insertAllCoins(coins: List<CoinDto>) = coinDao.insertAll(coins.map {it.coinDtoToModel() })

    suspend fun insertAllImages(images: List<ImageDto>) = imageDao.insertAll(images.map {it.imageDtoToModel() })

    fun getCategoriesByParentId(parentId: Int?) =
        parentId?.let { categoryDao.getCategoriesByParentId(parentId)} ?: run {categoryDao.getMainCategories()}

    fun getCoinsByCategoryId(categoryId: Int?)
    = categoryId?.let { coinDao.getCoinsByCategoryId(categoryId)} ?: run {coinDao.getMainCoins()}

    fun getImageById(imageId: Int) = imageDao.getImageById(imageId)

    fun updateCoin(coin: Coin) = coinDao.update(coin)
}