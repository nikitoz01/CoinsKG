package kg.mobile.coins.ui.fragment.categorycoin.coin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kg.mobile.coins.repository.RoomRepository
import kg.mobile.coins.room.model.Coin
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CoinViewModel @Inject constructor(private val repository: RoomRepository) :ViewModel(){

    var coinFlow: Flow<PagingData<Coin>>? = null

    fun getCoins(categoryId: Int?) {
        coinFlow = repository.
        getPagedCoinsByCategoryId(categoryId).cachedIn(viewModelScope)
    }

}