package kg.mobile.coins.dagger

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import kg.mobile.coins.repository.RoomRepository
import kg.mobile.coins.room.CoinsDatabase
import kg.mobile.coins.room.dao.CategoryDao
import kg.mobile.coins.room.dao.CoinDao
import javax.inject.Scope
import javax.inject.Singleton

@Module
class RoomModule {


    @Provides
    @Singleton
    fun provideCategoryDao(coinsDatabase: CoinsDatabase) = coinsDatabase.getCategoryDao()

    @Provides
    @Singleton
    fun provideImageDao(coinsDatabase: CoinsDatabase) = coinsDatabase.getImageDao()

    @Provides
    @Singleton
    fun provideCoinDao(coinsDatabase: CoinsDatabase) = coinsDatabase.getCoinDao()

    @Provides
    @Singleton
    fun provideRoomRepository(categoryDao: CategoryDao, coinDao: CoinDao) =
        RoomRepository(categoryDao, coinDao)

    @Provides
    @Singleton
    fun provideDatabase(context: Context): CoinsDatabase {
        return Room.databaseBuilder(context, CoinsDatabase::class.java, "coins_database")
            .fallbackToDestructiveMigration()
            .build()
    }
}

@Scope
@Retention(AnnotationRetention.RUNTIME)
public annotation class RoomScope {}