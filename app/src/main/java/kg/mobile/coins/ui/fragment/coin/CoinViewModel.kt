package kg.mobile.coins.ui.fragment.coin

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kg.mobile.coins.repository.RoomRepositoryImpl
import kg.mobile.coins.room.model.Coin
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class CoinViewModel @Inject constructor(private val repository: RoomRepositoryImpl) :ViewModel(){

    private val _coinLiveData = MutableLiveData<List<Coin>>()
    val coinLiveData
        get() = _coinLiveData

    fun loadImage(categoryId: Int?) {
        viewModelScope.launch (Dispatchers.IO){

        }
    }

    fun getCoins(categoryId: Int?) {
        viewModelScope.launch {
            repository.
            getCoinsByCategoryId(categoryId).
            collect {
                _coinLiveData.postValue(it)
            }
        }
    }

}