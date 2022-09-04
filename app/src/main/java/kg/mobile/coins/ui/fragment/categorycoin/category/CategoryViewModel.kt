package kg.mobile.coins.ui.fragment.categorycoin.category

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kg.mobile.coins.repository.RoomRepository
import kg.mobile.coins.room.model.Category
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class CategoryViewModel @Inject constructor(private val repository: RoomRepository) :ViewModel(){

    private val _categoriesChildLiveData = MutableLiveData<List<Category>>()
    val categoriesChildLiveData
        get() = _categoriesChildLiveData

    val categoryLiveData = MutableLiveData<Category>()

    fun getChildCategories(parentId: Int?) = viewModelScope.launch(Dispatchers.IO) {
            repository.
            getCategoriesByParentId(parentId).
            collect {
                _categoriesChildLiveData.postValue(it)
            }
        }


    fun getCategory(id: Int) = viewModelScope.launch(Dispatchers.IO){
            categoryLiveData.postValue(repository.
            getCategoryById(id))
        }

//    val mainCategories = repository.getMainCategories()
}