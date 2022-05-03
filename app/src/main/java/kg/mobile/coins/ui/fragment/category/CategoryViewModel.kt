package kg.mobile.coins.ui.fragment.category

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kg.mobile.coins.repository.RoomRepository
import kg.mobile.coins.room.model.Category
import kotlinx.coroutines.launch
import javax.inject.Inject

class CategoryViewModel @Inject constructor(private val repository: RoomRepository) :ViewModel(){

    private val _categoryLiveData = MutableLiveData<List<Category>>()
    val categoryLiveData
        get() = _categoryLiveData

    fun getCategories(parentId: Int?) {
        viewModelScope.launch {
            repository.
            getCategoriesByParentId(parentId).
            collect {
                _categoryLiveData.postValue(it)
            }
        }
    }
//    val mainCategories = repository.getMainCategories()
}