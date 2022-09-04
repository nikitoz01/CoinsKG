package kg.mobile.coins.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kg.mobile.coins.room.dao.CategoryDao
import kg.mobile.coins.room.dao.CoinDao
import kg.mobile.coins.room.model.Category
import kg.mobile.coins.room.model.Coin
import kg.mobile.coins.ui.fragment.categorycoin.coin.paging.CoinPagingSource
import kg.mobile.coins.ui.fragment.coinsearch.paging.CoinSearchPagingSource
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

    private suspend fun getCoinsByCategoryId(categoryId: Int?, limit: Int, offset: Int)
    = categoryId?.let { coinDao.getCoinsByCategoryId(categoryId, limit, offset)} ?: run {coinDao.getMainCoins(limit, offset)}

    private suspend fun getCoins(searchBy: String, limit: Int, offset: Int) = coinDao.getCoins(searchBy, limit, offset)
    private suspend fun getFavoriteCoins(searchBy: String, limit: Int, offset: Int) = coinDao.getFavoriteCoins(searchBy, limit, offset)
    private suspend fun getInCollectionCoins(searchBy: String, limit: Int, offset: Int) = coinDao.getInCollectionCoins(searchBy, limit, offset)

    suspend fun isAnyCoinsExist() = coinDao.isAnyCoinsExist()

    fun getPagedCoinsByCategoryId(categoryId: Int?): Flow<PagingData<Coin>> {
        return Pager(
            config = PagingConfig(
                pageSize = LOAD_SIZE,
                enablePlaceholders = false,
                initialLoadSize = LOAD_SIZE
            ),
            pagingSourceFactory = {CoinPagingSource(categoryId,::getCoinsByCategoryId) }
        ).flow
    }

    //mode = 0 - ALL
    //mode = 1 - Favorite
    //mode = 2 - inCollection
    fun getPagedCoins(searchBy: String, mode: Int = 0): Flow<PagingData<Coin>> {
        val getCoins = when(mode){
            0 -> ::getCoins
            1 -> ::getFavoriteCoins
            2 -> ::getInCollectionCoins
            else -> {::getCoins}
        }
        return Pager(
            config = PagingConfig(
                pageSize = LOAD_SIZE,
                enablePlaceholders = false,
                initialLoadSize =LOAD_SIZE
            ),
            pagingSourceFactory = { CoinSearchPagingSource(searchBy,getCoins) }
        ).flow
    }


    suspend fun getCoinById(coinId: Int) = coinDao.getById(coinId)
    suspend fun getCategoryById(categoryId: Int) = categoryDao.getById(categoryId)


    companion object{
        const val LOAD_SIZE = 10
    }
}