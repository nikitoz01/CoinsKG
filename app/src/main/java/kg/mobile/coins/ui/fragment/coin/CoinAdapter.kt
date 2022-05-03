package kg.mobile.coins.ui.fragment.coin

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kg.mobile.coins.databinding.ItemCoinBinding
import kg.mobile.coins.room.model.Coin
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class CoinAdapter(private val itemClick: (Coin)-> Unit ): PagingDataAdapter<Coin, CoinAdapter.CoinViewHolder>(CoinsDiffCallback()) {

    inner class CoinViewHolder (val binding: ItemCoinBinding): RecyclerView.ViewHolder(binding.root){
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinViewHolder {
      return CoinViewHolder(ItemCoinBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: CoinViewHolder, position: Int) {
        with(holder){
            with(getItem(position)?: return) {
                binding.coinCardView.setOnClickListener{
                    itemClick.invoke(this)
                }
                binding.coinNameTextView.text=name
                binding.coinDescriptionImageView.text=description
                CoroutineScope(Dispatchers.Main).launch {
                    Glide
                        .with(binding.coinImageView.context)
                        .load(imagePath)
                        .error("")
                        .into(binding.coinImageView)
                }
                }
            }
    }

}

class CoinsDiffCallback: DiffUtil.ItemCallback<Coin>(){
    override fun areItemsTheSame(oldItem: Coin, newItem: Coin): Boolean {
       return  oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Coin, newItem: Coin): Boolean {
        return oldItem == newItem
    }

}