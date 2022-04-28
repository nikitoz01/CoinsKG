package kg.mobile.coins.ui.fragment.coin

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kg.mobile.coins.databinding.ItemCoinBinding
import kg.mobile.coins.room.model.Coin
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class CoinAdapter (private val context: Context,
                   private val itemClick: (Coin)->(Unit)): RecyclerView.Adapter<CoinAdapter.CoinViewHolder>() {
    private  var coinsList: List<Coin> = listOf()

    inner class CoinViewHolder (val binding: ItemCoinBinding): RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinViewHolder {
      return CoinViewHolder(ItemCoinBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: CoinViewHolder, position: Int) {
        with(holder){
            with(coinsList[position]) {
                binding.coinCardView.setOnClickListener{
                    itemClick.invoke(this)
                }
                binding.coinNameTextView.text=name
                binding.coinDescriptionImageView.text=description
                CoroutineScope(Dispatchers.Main).launch {
                    Glide
                        .with(context)
                        .load(imagePath)
                        .error("")
                        .into(binding.coinImageView)
                }
                }
            }
    }
    override fun getItemCount() = coinsList.size

    fun setList(list: List<Coin>) {
        coinsList = list
        notifyDataSetChanged()
    }
}