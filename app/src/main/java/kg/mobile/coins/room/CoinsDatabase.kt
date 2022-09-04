package kg.mobile.coins.room

import androidx.room.Database
import androidx.room.RoomDatabase
import kg.mobile.coins.room.dao.CategoryDao
import kg.mobile.coins.room.dao.CoinDao
import kg.mobile.coins.room.dao.ImageDao
import kg.mobile.coins.room.model.Category
import kg.mobile.coins.room.model.Coin
import kg.mobile.coins.room.model.Image

@Database(entities = [Category::class, Coin::class, Image::class], version = 1)
abstract class CoinsDatabase: RoomDatabase() {

    abstract fun getCategoryDao(): CategoryDao

    abstract fun getCoinDao(): CoinDao

    abstract fun getImageDao(): ImageDao

}