package kg.mobile.coins.room.dao

import androidx.room.*
import kg.mobile.coins.room.model.Coin
import kotlinx.coroutines.flow.Flow

@Dao
interface CoinDao {
    @Query("SELECT * FROM coin where isActive = 1 and isImageLoaded = 1")
    fun getAllCoins(): Flow<List<Coin>>

    @Query("SELECT * FROM coin where isActive = 1 and isImageLoaded = 0")
    fun getAllCoinsWithoutImage(): Flow<List<Coin>>


    @Query("SELECT * FROM coin where categoryId IS NULL and isActive = 1 and isImageLoaded = 1")
    fun getMainCoins(): Flow<List<Coin>>

    @Query("SELECT * FROM coin where categoryId = :categoryId and isActive = 1 and isImageLoaded = 1")
    fun getCoinsByCategoryId(categoryId: Int): Flow<List<Coin>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(coin: Coin)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(coins: List<Coin>)

    @Update()
    fun update(coin: Coin)

}