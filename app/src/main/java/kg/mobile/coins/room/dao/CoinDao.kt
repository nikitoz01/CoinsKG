package kg.mobile.coins.room.dao

import androidx.room.*
import kg.mobile.coins.room.model.Coin
import kotlinx.coroutines.flow.Flow

@Dao
interface CoinDao {
    @Query("SELECT * FROM coin WHERE isActive = 1 and isImageLoaded = 1")
    fun getAllCoins(): Flow<List<Coin>>

    @Query("SELECT * FROM coin " +
    "WHERE isActive = 1 and isImageLoaded = 1 and " +
    "((:searchBy = '' OR lower(name) LIKE '%' || lower(:searchBy) || '%') or " +
            "(lower(mint) = lower(:searchBy)))  " +
    "ORDER BY name " +
    "LIMIT :limit OFFSET :offset ")
    suspend fun getCoins(searchBy: String,limit: Int, offset: Int): List<Coin>

    @Query("SELECT * FROM coin " +
            "WHERE isActive = 1 and isImageLoaded = 1 and " +
            "((:searchBy = '' OR lower(name) LIKE '%' || lower(:searchBy) || '%') or " +
            "(lower(mint) = lower(:searchBy)))  and " +
            "isFavorite = 1 " +
            "ORDER BY name " +
            "LIMIT :limit OFFSET :offset ")
    suspend fun getFavoriteCoins(searchBy: String,limit: Int, offset: Int): List<Coin>

    @Query("SELECT * FROM coin " +
            "WHERE isActive = 1 and isImageLoaded = 1 and " +
            "((:searchBy = '' OR lower(name) LIKE '%' || lower(:searchBy) || '%') or " +
            "(lower(mint) = lower(:searchBy)))  and " +
            "isInCollection = 1 " +
            "ORDER BY name " +
            "LIMIT :limit OFFSET :offset ")
    suspend fun getInCollectionCoins(searchBy: String,limit: Int, offset: Int): List<Coin>

//    @Query("SELECT * FROM coin " +
//            "WHERE categoryId IS NULL and isActive = 1 and isImageLoaded = 1")
//    fun getMainCoins(): Flow<List<Coin>>
//
//    @Query("SELECT * FROM coin where categoryId = :categoryId AND isActive = 1 AND isImageLoaded = 1")
//    fun getCoinsByCategoryId(categoryId: Int): Flow<List<Coin>>

    @Query("SELECT * FROM coin " +
            "WHERE categoryId IS NULL and isActive = 1 and isImageLoaded = 1 " +
            "LIMIT :limit OFFSET :offset")
    suspend fun getMainCoins(limit: Int, offset: Int): List<Coin>

    @Query("SELECT * FROM coin " +
            "where categoryId = :categoryId AND isActive = 1 AND isImageLoaded = 1 " +
            "LIMIT :limit OFFSET :offset")
    suspend fun getCoinsByCategoryId(categoryId: Int, limit: Int, offset: Int): List<Coin>


    @Query("SELECT * FROM coin WHERE isActive = 1 AND isImageLoaded = 0")
    suspend fun getAllCoinsWithoutImage(): List<Coin>

    @Query("SELECT * FROM coin WHERE id = :coinId")
    suspend fun getById(coinId: Int): Coin

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(coin: Coin)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(coins: List<Coin>)

    @Update()
    suspend fun update(coin: Coin)

    @Update()
    suspend fun update(coins: List<Coin>)

    @Delete()
    suspend fun delete(coin: Coin)

    @Delete()
    suspend fun delete(coins: List<Coin>)

    @Query("Select EXISTS(Select * from Coin) ")
    suspend fun isAnyCoinsExist(): Boolean

}