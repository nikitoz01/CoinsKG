package kg.mobile.coins.ui.fragment.coin

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kg.mobile.coins.repository.RoomRepository
import kg.mobile.coins.room.model.Coin
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

class CoinViewModel @Inject constructor(private val repository: RoomRepository) :ViewModel(){

    private val _coinLiveData = MutableLiveData("")

    var coinFlow: Flow<PagingData<Coin>>? = null

    fun getCoins(categoryId: Int?) {
        coinFlow = _coinLiveData.asFlow()
            .flatMapLatest{repository.getPagedCoinsByCategoryId(categoryId)}
            .cachedIn(viewModelScope)
    }

}