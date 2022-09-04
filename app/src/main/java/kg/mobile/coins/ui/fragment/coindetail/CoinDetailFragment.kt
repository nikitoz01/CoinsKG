package kg.mobile.coins.ui.fragment.coindetail

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Paint
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import kg.mobile.coins.R
import kg.mobile.coins.appComponent
import kg.mobile.coins.circularProgressDrawable
import kg.mobile.coins.dagger.vmfactory.MultiViewModelFactory
import kg.mobile.coins.databinding.FragmentCoinDetailBinding
import kg.mobile.coins.room.model.Coin
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

    private val navArgs by navArgs<CoinDetailFragmentArgs>()

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
        val coinId: Int = navArgs.coinId
        coinDetailViewModel.getCoins(coinId)
        coinDetailViewModel.coinDetailLiveData.observe(viewLifecycleOwner){
            showInfo(it)
        }
        super.onViewCreated(view, savedInstanceState)
    }


    private fun showInfo(coin: Coin) {
        coin.apply {
            setInfo(binding.coinDetailNameTextView, name)
            binding.zenoTextView.apply {
                setInfo(this, binding.zenoContainer, zenoId?.toString())
                setTextColor(Color.BLUE)
                paintFlags = Paint.UNDERLINE_TEXT_FLAG
                setOnClickListener{
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://www.zeno.ru/showphoto.php?photo=${zenoId}")))
                }
            }
            binding.buttonCloseDialog.setOnClickListener {
                findNavController().popBackStack()
            }

            Glide
                .with(activity?.applicationContext!!)
                .load(imagePath)
                .placeholder(requireContext().circularProgressDrawable())
                .error(R.drawable.no_image)
                .into(binding.coinDetailImageView)

            setInfo(binding.yearTextView, binding.yearContainer, year)
            setInfo(binding.sizeTextView, binding.sizeContainer, size)
            setInfo(binding.weightTextView, binding.weightContainer, weight)
            setInfo(binding.mintTextView, binding.mintContainer, mint)
            setInfo(binding.estimateTextView, binding.estimateContainer, estimate)
            setInfo(binding.rarityTextView, binding.rarityContainer, rarity)
            setInfo(binding.denominationTextView, binding.denominationContainer, denomination)
            setInfo(binding.metalTextView, binding.metalContainer, metal)

            auctionURL.let{
                binding.buttonToAuction.apply {
                    setOnClickListener {
                        startActivity(
                            Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse( auctionURL)
                            )
                        )
                    }
                    visibility = View.VISIBLE
                }
            }
        }
    }

    private fun  setInfo(view: TextView, info: String?){
        info?.let{
            view.text = info
            view.visibility = View.VISIBLE
        }
    }

    private fun  setInfo(view: TextView, container: LinearLayout, info: String?){
        info?.let{
            view.text = info
            container.visibility = View.VISIBLE
        }
    }

    companion object {
        fun newInstance(coinId: Int): CoinDetailFragment {
            val bundle = Bundle()
            bundle.putInt("coinId", coinId)
            val fragment = CoinDetailFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}