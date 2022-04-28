package kg.mobile.coins.ui.fragment

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kg.mobile.coins.model.State
import kg.mobile.coins.repository.GlideRepositoryImpl
import kg.mobile.coins.repository.RestRepositoryImpl
import kg.mobile.coins.repository.RoomRepositoryImpl
import kg.mobile.coins.retrofit.dto.CategoryDto
import kg.mobile.coins.retrofit.dto.CoinDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


class MainFragmentViewModel @Inject constructor(private val restRepository: RestRepositoryImpl,
                                                private val roomRepository: RoomRepositoryImpl,
                                                private val glideRepository: GlideRepositoryImpl,
                                                private val sharedPreferences: SharedPreferences): ViewModel() {
    private val _categoryStateFlow: MutableStateFlow<State<List<CategoryDto>>?> = MutableStateFlow(null)
    val categoryStateFlow : StateFlow<State<List<CategoryDto>>?>
        get() = _categoryStateFlow

    private val _coinStateFlow: MutableStateFlow<State<List<CoinDto>>?> = MutableStateFlow(null)
    val coinStateFlow : StateFlow<State<List<CoinDto>>?>
        get() = _coinStateFlow


    private val categoryUpdateTime = sharedPreferences.getLong("categoryUpdateTime",0)
    private val coinUpdateTime = sharedPreferences.getLong("coinUpdateTime",0)

    private var categoryJob = getAllCategories()
    private var coinJob = getAllCoins()


    fun cancelLoad(){
        categoryJob.cancel()
        coinJob.cancel()
    }

    fun  updateSharedPref (type: String, value:Long){
        sharedPreferences.
        edit().
        putLong(type, value).
        apply()
    }

    fun retryLoad () {
        if (categoryJob.isCompleted) categoryJob = getAllCategories()
        if (coinJob.isCompleted) coinJob = getAllCoins()
    }

    fun insertCategories(categories : List<CategoryDto>){
        viewModelScope.launch(Dispatchers.IO) {
            roomRepository.insertAllCategories(categories)
        }
    }

    fun insertCoins(coins : List<CoinDto>){
        viewModelScope.launch(Dispatchers.IO) {
            roomRepository.insertAllCoins(coins)
            glideRepository.loadImage()
        }
    }

    private fun getAllCategories() = viewModelScope.launch {
        restRepository.
        getNewCategories(categoryUpdateTime).
        collect{
            _categoryStateFlow.value = it }
    }



    private fun getAllCoins() =  viewModelScope.launch{
        restRepository.
        getNewCoins(coinUpdateTime).
        collect{
            _coinStateFlow.value = it }
    }

}