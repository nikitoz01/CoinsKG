package kg.mobile.coins.ui.fragment.categorycoin

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

class CategoryCoinViewModel(private val state: SavedStateHandle): ViewModel() {

    fun getParentId() = state.getLiveData<Int?>("parentId")

    fun getCategoryName() = state.getLiveData<String?>("categoryName")

    fun setData(parentId: Int?, categoryName: String?) {
        state["parentId"] = parentId
        state["categoryName"] = categoryName
    }

}