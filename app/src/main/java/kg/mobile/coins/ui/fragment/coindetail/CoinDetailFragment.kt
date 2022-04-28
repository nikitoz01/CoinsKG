package kg.mobile.coins.ui.fragment.coindetail

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import kg.mobile.coins.R
import kg.mobile.coins.appComponent
import kg.mobile.coins.dagger.vmfactory.MultiViewModelFactory
import kg.mobile.coins.databinding.FragmentCoinDetailBinding
import javax.inject.Inject

class CoinDetailFragment : DialogFragment(R.layout.fragment_coin_detail) {

    private var _binding: FragmentCoinDetailBinding? = null
    private val binding
    get() = _binding!!


    private val coinDetailViewModel: CoinDetailViewModel by viewModels {
        factory
    }


    @Inject
    lateinit var factory : MultiViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCoinDetailBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }


    override fun onAttach(context: Context) {
        context.appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
         arguments?.let { showInfo(CoinDetailFragmentArgs.fromBundle(it).coinId) }
        super.onViewCreated(view, savedInstanceState)
    }

    private fun showInfo(coinId: Int){

    }

    companion object {
        fun newInstance(coinId: Int): CoinDetailFragment {
            val bundle = Bundle()
            bundle.putString("coinId", coinId.toString())
            val fragment = CoinDetailFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}