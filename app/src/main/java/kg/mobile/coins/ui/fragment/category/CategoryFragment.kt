package kg.mobile.coins.ui.fragment.category

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import kg.mobile.coins.R
import kg.mobile.coins.appComponent
import kg.mobile.coins.dagger.vmfactory.MultiViewModelFactory
import kg.mobile.coins.databinding.FragmentCategoryBinding
import kg.mobile.coins.ui.fragment.coin.CoinFragmentDirections
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

        adapter = CategoryAdapter{category ->
            parentFragmentManager.apply {
                commit {
                    replace(R.id.categoryFragmentContainerView, newInstance(category.id, category.name))
                    // replace(R.id.coinFragmentContainerView, CoinFragment.newInstance(it.id))
                    setReorderingAllowed(true)
                    addToBackStack(null)
                }
                findFragmentById(R.id.coinFragmentContainerView)?.let {
                    (it as NavHostFragment).navController.navigate(CoinFragmentDirections.actionGlobalCoinFragment(category.id.toString()))
                }
            }
        }

        recyclerview.adapter = adapter
        return categoryBinding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val  parentId: Int? = arguments?.getString("parentId")?.toInt()
        val  categoryName: String? = arguments?.getString("categoryName")
        categoryName?.let {
            (activity as AppCompatActivity).supportActionBar?.title = it
        } ?: run {(activity as AppCompatActivity).supportActionBar?.title = "CoinsKG"}

        categoryViewModel.getCategories(parentId)

        categoryViewModel.categoryLiveData.observe(viewLifecycleOwner) {
            adapter.setList(it)
        }
    }

    companion object {
        fun newInstance(parentId: Int?, categoryName: String?): CategoryFragment {
            val bundle = Bundle()
            bundle.putString("parentId", parentId.toString())
            bundle.putString("categoryName", categoryName)
            val fragment = CategoryFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}
