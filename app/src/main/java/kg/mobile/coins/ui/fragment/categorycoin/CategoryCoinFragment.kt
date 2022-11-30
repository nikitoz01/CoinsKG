package kg.mobile.coins.ui.fragment.categorycoin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import kg.mobile.coins.R

class CategoryCoinFragment : Fragment() {

    private val categoryCoinViewModel: CategoryCoinViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    private val navArgs by navArgs<CategoryCoinFragmentArgs>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_category_coin, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var parentId: Int? = navArgs.parentId
        if (parentId == -1) parentId = null

        val categoryName = navArgs.categoryName
        categoryCoinViewModel.setData(parentId, categoryName)
    }

}