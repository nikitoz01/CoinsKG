package kg.mobile.coins.ui.fragment.coinsearch

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kg.mobile.coins.dagger.vmfactory.FragmentScope
import kg.mobile.coins.repository.RoomRepository
import kg.mobile.coins.room.model.Coin
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@FragmentScope
class CoinSearchViewModel @Inject constructor(private val repository: RoomRepository) :
    ViewModel() {

    var coinSearchFlow: Flow<PagingData<Coin>>

    private val searchBy = MutableLiveData("")

    private val mode = MutableLiveData(0)

    init {
        coinSearchFlow = searchBy.asFlow()
            .debounce(500)
            .flatMapLatest {
                repository.getPagedCoins(it, mode.value!!)
            }
            .cachedIn(viewModelScope)
    }

    fun setSearchBy(value: String, mode: Int = 0) {
        if ((this.searchBy.value == value) && (this.mode.value == mode)) return

        this.mode.value = mode
        this.searchBy.value = value

    }

    fun updateCoin(coin: Coin) = viewModelScope.launch(Dispatchers.IO) {
        repository.updateCoin(coin)
    }

}