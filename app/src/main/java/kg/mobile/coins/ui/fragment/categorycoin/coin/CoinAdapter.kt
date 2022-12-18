package kg.mobile.coins.ui.fragment.categorycoin.coin

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.core.content.ContextCompat
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kg.mobile.coins.R
import kg.mobile.coins.circularProgressDrawable
import kg.mobile.coins.databinding.ItemCoinBinding
import kg.mobile.coins.room.model.Coin
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class CoinAdapter(private val itemClick: (Coin) -> Unit,
                  private val updateCoin: (Coin) -> Unit) :
    PagingDataAdapter<Coin, CoinAdapter.CoinViewHolder>(CoinsDiffCallback()) {

    inner class CoinViewHolder(val binding: ItemCoinBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinViewHolder {
        return CoinViewHolder(
            ItemCoinBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CoinViewHolder, position: Int) {
        with(holder) {
            with(getItem(position) ?: return) coin@ {
                binding.coinCardView.setOnClickListener {
                    itemClick.invoke(this)
                }
                binding.coinNameTextView.text = name
                year?.let { binding.coinPeriodTextView.text = it }
                estimate?.let { binding.coinEstimateTextView.text = it }
                CoroutineScope(Dispatchers.Main).launch {
                    Glide
                        .with(binding.coinImageView.context)
                        .load(imagePath)
                        .placeholder(binding.coinImageView.context.circularProgressDrawable())
                        .error(R.drawable.no_image)
                        .into(binding.coinImageView)
                }

                binding.favoriteCoinButton.apply {
                    setOnClickListener {
                        isFavorite = !isFavorite
                        checkToggle(this, isFavorite, R.drawable.ic_star)
                        updateCoin(this@coin)
                    }
                    checkToggle(this, isFavorite, R.drawable.ic_star)
                }
                binding.inCollectionCoinButton.apply {
                    setOnClickListener {
                        isInCollection = !isInCollection
                        checkToggle(this, isInCollection, R.drawable.ic_chest)
                        updateCoin(this@coin)
                    }
                    checkToggle(this, isInCollection, R.drawable.ic_chest)
                }
            }
        }
    }

    private fun checkToggle(imageButton: ImageButton, check: Boolean, resId: Int) {
        if (check) {
            setImageButtonIcon(imageButton, resId, R.color.active)
        } else {
            setImageButtonIcon(imageButton, resId, R.color.inactive)
        }
    }

    private fun setImageButtonIcon(imageButton: ImageButton, resId: Int, colorId: Int) {
        imageButton.apply {
            setImageResource(resId)
            imageTintList = ColorStateList.valueOf(ContextCompat.getColor(context, colorId))
        }
    }
}

class CoinsDiffCallback : DiffUtil.ItemCallback<Coin>() {
    override fun areItemsTheSame(oldItem: Coin, newItem: Coin): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Coin, newItem: Coin): Boolean {
        return oldItem == newItem
    }

}