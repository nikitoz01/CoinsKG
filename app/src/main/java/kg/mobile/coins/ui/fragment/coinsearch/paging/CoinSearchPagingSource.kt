package kg.mobile.coins.ui.fragment.coinsearch.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import kg.mobile.coins.room.model.Coin

class CoinSearchPagingSource(private val searchBy: String,
                             private val loader: suspend (String, Int, Int) -> List<Coin>
) : PagingSource<Int, Coin>() {
    override fun getRefreshKey(state: PagingState<Int, Coin>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Coin> {
        val pageIndex = params.key ?: 0
        val coins = loader.invoke(searchBy,  params.loadSize, params.loadSize * pageIndex)
        return LoadResult.Page(
            data = coins,
            prevKey = if (pageIndex == 0) null else pageIndex - 1,
            nextKey = if (coins.size == params.loadSize) pageIndex + 1 else null
        )
    }
}