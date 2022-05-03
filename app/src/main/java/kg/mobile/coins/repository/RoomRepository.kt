package kg.mobile.coins.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kg.mobile.coins.room.dao.CategoryDao
import kg.mobile.coins.room.dao.CoinDao
import kg.mobile.coins.room.model.Category
import kg.mobile.coins.room.model.Coin
import kg.mobile.coins.ui.fragment.coin.paging.CoinPagingSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RoomRepository @Inject constructor(
    private val categoryDao: CategoryDao,
    private val coinDao: CoinDao) {

    fun getAllCategories() = categoryDao.getAllCategories()

    fun getAllCoins() = coinDao.getAllCoins()

    suspend fun getAllCoinsWithoutImage() = coinDao.getAllCoinsWithoutImage()

    suspend fun insertCategories(categories: List<Category>) = categoryDao.insert(categories)
    suspend fun insertCoins(coins: List<Coin>) = coinDao.insert(coins)

    suspend fun updateCoin(coin: Coin) = coinDao.update(coin)
    suspend fun updateCoin(coins: List<Coin>) = coinDao.update(coins)

    fun getCategoriesByParentId(parentId: Int?) =
        parentId?.let { categoryDao.getCategoriesByParentId(parentId)} ?: run {categoryDao.getMainCategories()}

    suspend fun getCoinsByCategoryId(categoryId: Int?, limit: Int, offset: Int)
    = categoryId?.let { coinDao.getCoinsByCategoryId(categoryId, limit, offset)} ?: run {coinDao.getMainCoins(limit, offset)}

    suspend fun getPagedCoinsByCategoryId(categoryId: Int?): Flow<PagingData<Coin>> {
        val loader = ::getCoinsByCategoryId
        return Pager(
            config = PagingConfig(
                pageSize = LOAD_SIZE,
                enablePlaceholders = false,
                initialLoadSize =LOAD_SIZE
            ),
            pagingSourceFactory = {CoinPagingSource(loader, categoryId)}
        ).flow
    }


    suspend fun getCoinById(coinId: Int) = coinDao.getById(coinId)

    companion object{
        const val LOAD_SIZE = 10
    }
}