package kg.mobile.coins

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import kg.mobile.coins.retrofit.CoinsRestApi
import kg.mobile.coins.room.model.Category
import kg.mobile.coins.room.model.Coin
import kg.mobile.coins.util.categoryDtoToModel
import kg.mobile.coins.util.coinDtoToModel
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


@RunWith(AndroidJUnit4::class)
class RetrofitTest {
    private lateinit var retrofit: Retrofit

    private lateinit var api: CoinsRestApi

    private fun testCoin() = Coin(
        1, null, "Джитал Газни", 295000, "Gao-Chang-Ji-Li.jpg", null,
        null, null, "1206-1227", null, null, "AE",
        "Газни", "1 джитал", null, null, 1653745336, true
    )


    private fun testCategory() = Category(
        22, "Карлукский Каганат", null, "756 — 940", null,
        null, 1653762273, false
    )

    lateinit var context: Context

    @Before
    fun createRetrofit() {
        context = ApplicationProvider.getApplicationContext<Context>()
        retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        api = retrofit.create(CoinsRestApi::class.java)
    }

    @Test
    @Throws(Exception::class)
    fun coinByUpdateTimeTest1() {
        runBlocking {
            assertThat(api.getCoins(1653745335).get(0).coinDtoToModel()).isEqualTo(testCoin())
        }
    }

    @Test
    @Throws(Exception::class)
    fun coinByUpdateTimeTest2() {
        runBlocking {
            assertThat(api.getCoins(10000000000)).isEqualTo(emptyList<Coin>())
        }
    }

    @Test
    @Throws(Exception::class)
    fun categoryByUpdateTimeTest1() {
        runBlocking {
            assertThat(api.getCategories(1652876364).get(0).categoryDtoToModel()).isEqualTo(
                testCategory()
            )
        }
    }

    @Test
    @Throws(Exception::class)
    fun categoryByUpdateTimeTest2() {
        runBlocking {
            assertThat(api.getCategories(10000000000)).isEqualTo(emptyList<Category>())
        }
    }


}