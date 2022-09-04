package kg.mobile.coins

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import kg.mobile.coins.room.CoinsDatabase
import kg.mobile.coins.room.dao.CategoryDao
import kg.mobile.coins.room.dao.CoinDao
import kg.mobile.coins.room.model.Category
import kg.mobile.coins.room.model.Coin
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException


@RunWith(AndroidJUnit4::class)
class DBRoomTest {
    private lateinit var coinDao: CoinDao
    private lateinit var categoryDao: CategoryDao

    private lateinit var db: CoinsDatabase


    private fun testCoin() = Coin(1, 1 , "Да Ли Юань Бао", 295000, "No-Image.jpg", null, null,
        null, "766–780", null, null ,"AE" ,null ,null ,null,
        null, 0, true)


    private fun testCategory() = Category(1, "Карлукский Каганат", null, "756 — 940", null,
        null, 1, true)

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, CoinsDatabase::class.java).build()
        coinDao = db.getCoinDao()
        categoryDao = db.getCategoryDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertCoinTest() {
        runBlocking {
            coinDao.insert(testCoin())
            assertThat(coinDao.getById(1)).isEqualTo(testCoin())
        }
    }

    @Test
    @Throws(Exception::class)
    fun insertCategoryTest() {
        runBlocking {
             categoryDao.insert(testCategory())
            assertThat(categoryDao.getById(1)).isEqualTo(testCategory())
        }
    }


    @Test
    @Throws(Exception::class)
    fun deleteCoinTest() {
        runBlocking {
            coinDao.insert(testCoin())
            coinDao.delete(testCoin())
            assertThat(coinDao.getById(1)).isEqualTo(null)
        }
    }

    @Test
    @Throws(Exception::class)
    fun deleteCategoryTest() {
        runBlocking {
            categoryDao.insert(testCategory())
            categoryDao.delete(testCategory())
            assertThat(categoryDao.getById(1)).isEqualTo(null)
        }
    }

    @Test
    @Throws(Exception::class)
    fun updateCoinTest() {
        runBlocking {
            coinDao.insert(testCoin())
            coinDao.update(testCoin().apply {
                isFavorite = true
                isImageLoaded = true
                isActive = true
            })
            assertThat(coinDao.getFavoriteCoins("",10,0).get(0)).isEqualTo(testCoin().apply {
                isFavorite = true
                isImageLoaded = true
                isActive = true
            })
        }
    }


}