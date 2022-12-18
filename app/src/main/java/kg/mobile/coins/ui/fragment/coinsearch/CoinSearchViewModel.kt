package kg.mobile.coins.ui.fragment.coinsearch

import androidx.lifecycle.*
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

    private val searchByLiveData = MutableLiveData("")

    private val modeLiveData  = MutableLiveData(0)

    private val mediatorLiveData = MediatorLiveData<String>()

    init {
        mediatorLiveData.addSource(searchByLiveData) { mediatorLiveData.value = it }
        mediatorLiveData.addSource(modeLiveData) { mediatorLiveData.value = searchByLiveData.value }
    }

    var coinSearchFlow: Flow<PagingData<Coin>> = mediatorLiveData.asFlow()
        .debounce(500)
        .flatMapLatest {
            repository.getPagedCoins(it, modeLiveData.value!!)
        }
        .cachedIn(viewModelScope)



    fun updateMode(newMode: Int){
        modeLiveData.postValue(newMode)
    }

    fun updateSearchBy(newSearchBy: String){
        searchByLiveData.postValue(newSearchBy)
    }

    fun updateCoin(coin: Coin) = viewModelScope.launch(Dispatchers.IO) {
        repository.updateCoin(coin)
    }

}