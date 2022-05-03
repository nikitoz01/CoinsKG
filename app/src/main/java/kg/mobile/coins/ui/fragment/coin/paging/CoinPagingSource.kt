package kg.mobile.coins.ui.fragment.coin.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import kg.mobile.coins.room.model.Coin

class CoinPagingSource(
    private val loader: suspend (Int?, Int, Int) -> List<Coin>,
    private val categoryId: Int?,
    ) :PagingSource<Int, Coin>() {

    override fun getRefreshKey(state: PagingState<Int, Coin>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Coin> {
        val pageIndex = params.key ?: 0
        val coins = loader.invoke(categoryId,  params.loadSize, params.loadSize * pageIndex)
        // success! now we can return LoadResult.Page
        return LoadResult.Page(
            data = coins,
            // index of the previous page if exists
            prevKey = if (pageIndex == 0) null else pageIndex - 1,
            // index of the next page if exists;
            // please note that 'params.loadSize' may be larger for the first load (by default x3 times)
            nextKey = if (coins.size == params.loadSize) pageIndex + 1 else null
        )
    }
}