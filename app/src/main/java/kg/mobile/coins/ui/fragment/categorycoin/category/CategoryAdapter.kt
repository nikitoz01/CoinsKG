package kg.mobile.coins.ui.fragment.categorycoin.category

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kg.mobile.coins.databinding.ItemCategoryBinding
import kg.mobile.coins.room.model.Category

class CategoryAdapter(private val itemClick: ((Category)-> Unit)): RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {
    private  var categoriesList: List<Category> = listOf()

    inner class CategoryViewHolder (val binding: ItemCategoryBinding): RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
      return CategoryViewHolder(ItemCategoryBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        with(holder){
            with(categoriesList[position]) {
                binding.categoryNameTextView.text=name
                binding.categoryPeriodTextView.text=period
                binding.categoryCardView.setOnClickListener{
                    itemClick.invoke(this)
                }
            }
        }
    }
    override fun getItemCount() = categoriesList.size

    fun setList(list: List<Category>) {
        categoriesList = list
        notifyDataSetChanged()
    }

}
