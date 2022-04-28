package kg.mobile.coins.repository

import kg.mobile.coins.model.State
import kg.mobile.coins.retrofit.CoinsRestApi
import kg.mobile.coins.retrofit.dto.CategoryDto
import kg.mobile.coins.retrofit.dto.CoinDto
import kg.mobile.coins.retrofit.dto.ImageDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RestRepositoryImpl @Inject constructor(private val restApi: CoinsRestApi) {
     fun getNewCategories(updateTime: Long): Flow<State<List<CategoryDto>>> = flow {
        val state = try {
            emit(State.Loading)
            val response = restApi.getCategories(updateTime)
            State.Success(response)
        } catch (e: Exception) {
            State.Fail(e)
        }
        emit(state)
    }
    fun getNewCoins(updateTime: Long): Flow<State<List<CoinDto>>> = flow {
        val state = try {
            emit(State.Loading)
            val response = restApi.getCoins(updateTime)
            State.Success(response)
        } catch (e: Exception) {
            State.Fail(e)
        }
        emit(state)
    }

    fun getNewImages(updateTime: Long): Flow<State<List<ImageDto>>> = flow {
        val state = try {
            emit(State.Loading)
            val response = restApi.getImages(updateTime)
            State.Success(response)
        } catch (e: Exception) {
            State.Fail(e)
        }
        emit(state)
    }

    fun getMainCategories(): Flow<State<List<CategoryDto>>> = flow {
        val state = try {
            emit(State.Loading)
            val response = restApi.getMainCategories()
            State.Success(response)
        } catch (e: Exception) {
            State.Fail(e)
        }
        emit(state)
    }

    fun getMainCoins(): Flow<State<List<CoinDto>>> = flow {
        val state = try {
            emit(State.Loading)
            val response = restApi.getMainCoins()
            State.Success(response)
        } catch (e: Exception) {
            State.Fail(e)
        }
        emit(state)
    }

}