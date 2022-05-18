package kg.mobile.coins.ui.fragment.coinsearch

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kg.mobile.coins.repository.RoomRepository
import kg.mobile.coins.room.model.Coin
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

class CoinSearchViewModel @Inject constructor(private val repository: RoomRepository) :ViewModel() {

    var coinSearchFlow: Flow<PagingData<Coin>>

    private val searchBy = MutableLiveData("")

    init {
        coinSearchFlow = searchBy.asFlow()
            .debounce(500)
            .flatMapLatest {
                repository.getPagedCoins(it)
            }
            .cachedIn(viewModelScope)
    }

    fun setSearchBy(value: String) {
        if (this.searchBy.value == value) return
        this.searchBy.value = value
    }


}