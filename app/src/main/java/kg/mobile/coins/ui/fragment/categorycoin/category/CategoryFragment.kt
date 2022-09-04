package kg.mobile.coins.ui.fragment.categorycoin.category

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kg.mobile.coins.R
import kg.mobile.coins.appComponent
import kg.mobile.coins.dagger.vmfactory.MultiViewModelFactory
import kg.mobile.coins.databinding.FragmentCategoryBinding
import kg.mobile.coins.ui.fragment.categorycoin.CategoryCoinFragment
import kg.mobile.coins.ui.fragment.categorycoin.CategoryCoinFragmentDirections
import kg.mobile.coins.ui.fragment.categorycoin.CategoryCoinViewModel
import javax.inject.Inject

class CategoryFragment : Fragment(R.layout.fragment_category) {

    private var _categoryBinding: FragmentCategoryBinding? = null

    private val categoryBinding
        get() = _categoryBinding!!

    @Inject
    lateinit var viewModelFactory: MultiViewModelFactory

    private val categoryViewModel: CategoryViewModel by viewModels {
        viewModelFactory
    }

    private val parentViewModel: CategoryCoinViewModel by viewModels({requireParentFragment()})

    private lateinit var adapter: CategoryAdapter

    override fun onAttach(context: Context) {
        context.appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onDestroyView() {
        _categoryBinding = null
        super.onDestroyView()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _categoryBinding = FragmentCategoryBinding.inflate(layoutInflater, container, false)
        val recyclerview = categoryBinding.categoryRecyclerView
        recyclerview.layoutManager = LinearLayoutManager(activity)

        adapter = CategoryAdapter {category ->
            (parentFragment as CategoryCoinFragment).
            findNavController().navigate(CategoryCoinFragmentDirections.
            actionGlobalCategoryCoinFragment(category.id, category.name))}

        recyclerview.adapter = adapter
        return categoryBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        parentViewModel.getParentId().observe(viewLifecycleOwner){
            categoryViewModel.getChildCategories(it)
            it?.let{categoryViewModel.getCategory(it)}
        }
        parentViewModel.getCategoryName().observe(viewLifecycleOwner) {
            it?.let {
                (activity as AppCompatActivity).supportActionBar?.title = it
            } ?: run { (activity as AppCompatActivity).supportActionBar?.title = "CoinsKG" }
        }

        categoryViewModel.categoriesChildLiveData.observe(viewLifecycleOwner) {
            if (it.isEmpty()) {
                categoryBinding.titleCategoryTextView.visibility = View.GONE
                categoryBinding.categoryRecyclerView.visibility = View.GONE
            }
            adapter.setList(it)
        }

        categoryViewModel.categoryLiveData.observe(viewLifecycleOwner){
            it.description?.let {  categoryBinding.currentCategoryDescriptionTextView.apply {
                text = it
                visibility = View.VISIBLE
            } }
        }
    }

    companion object {
        fun newInstance(parentId: Int?, categoryName: String?): CategoryFragment {
            val bundle = Bundle()
            bundle.putString("parentId", parentId?.toString() ?: "")
            bundle.putString("categoryName", categoryName)
            val fragment = CategoryFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

}
