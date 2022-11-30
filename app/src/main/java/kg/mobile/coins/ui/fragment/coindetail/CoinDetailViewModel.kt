package kg.mobile.coins.ui.fragment.coindetail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kg.mobile.coins.dagger.vmfactory.FragmentScope
import kg.mobile.coins.repository.RoomRepository
import kg.mobile.coins.room.model.Coin
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@FragmentScope
class CoinDetailViewModel @Inject constructor(private val roomRepository: RoomRepository) :
    ViewModel() {
    private val _coinDetailLiveData = MutableLiveData<Coin>()
    val coinDetailLiveData
        get() = _coinDetailLiveData

    fun getCoins(coinId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            coinDetailLiveData.postValue(
                roomRepository.getCoinById(coinId)
            )
        }
    }
}