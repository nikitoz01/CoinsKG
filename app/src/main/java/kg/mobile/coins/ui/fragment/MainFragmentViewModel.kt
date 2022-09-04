package kg.mobile.coins.ui.fragment

import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kg.mobile.coins.model.State
import kg.mobile.coins.repository.ApiRepository
import kg.mobile.coins.repository.GlideRepository
import kg.mobile.coins.repository.RoomRepository
import kg.mobile.coins.retrofit.dto.CategoryDto
import kg.mobile.coins.room.model.Coin
import kg.mobile.coins.util.categoryDtoToModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


class MainFragmentViewModel @Inject constructor(private val apiRepository: ApiRepository,
                                                private val roomRepository: RoomRepository,
                                                private val glideRepository: GlideRepository,
                                                private val sharedPreferences: SharedPreferences): ViewModel() {

    private val _categoryStateFlow: MutableStateFlow<State<List<CategoryDto>>?> = MutableStateFlow(null)
    val categoryStateFlow : StateFlow<State<List<CategoryDto>>?>
        get() = _categoryStateFlow

    private val _coinStateFlow: MutableStateFlow<State<List<Coin>>?> = MutableStateFlow(null)
    val coinStateFlow : StateFlow<State<List<Coin>>?>
        get() = _coinStateFlow


    private var categoryJob: Job? = null
    private var coinJob :Job? = null

    fun loadNewData () {
        categoryJob?.let {
            if (it.isCompleted) categoryJob = getNewCategories()
        } ?: run {
            categoryJob = getNewCategories()
        }
        coinJob?.let {
            if (it.isCompleted) coinJob = getNewCoins()
        } ?: run {
            coinJob = getNewCoins()
        }
    }


    fun insertCategories(categories : List<CategoryDto>){
        viewModelScope.launch(Dispatchers.IO) {
            roomRepository.insertCategories(categories.map{it.categoryDtoToModel()})
            categories.maxByOrNull {it.updateTime}?.let { updateSharedPref("categoryUpdateTime", it.updateTime )}
        }
    }

    fun insertCoins(coins : List<Coin>) = viewModelScope.launch(Dispatchers.IO) {
        roomRepository.insertCoins(coins)
        coins.maxByOrNull {it.updateTime}?.let { updateSharedPref("coinUpdateTime", it.updateTime )}
    }


    private fun getNewCategories() = viewModelScope.launch(Dispatchers.IO)  {
        val categoryUpdateTime = sharedPreferences.getLong("categoryUpdateTime",0)
        apiRepository.
        getNewCategories(categoryUpdateTime).
        collect{
            _categoryStateFlow.value = it }
    }


    private fun getNewCoins() =  viewModelScope.launch(Dispatchers.IO){
        val coinUpdateTime = sharedPreferences.getLong("coinUpdateTime",0)
        apiRepository.
        getNewCoins(coinUpdateTime).
        collect{
            _coinStateFlow.value = it
        }
    }

    private fun  updateSharedPref (type: String, value:Long){
        sharedPreferences.
        edit().
        putLong(type, value).
        apply()
    }

    val existsCheck = MutableLiveData<Boolean>()

    fun isAnyCoinsExists() = viewModelScope.launch(Dispatchers.IO) {
        existsCheck.postValue(roomRepository.
        isAnyCoinsExist())
    }

}