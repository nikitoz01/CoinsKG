package kg.mobile.coins.dagger.subcomponent

import dagger.Subcomponent
import kg.mobile.coins.dagger.RetrofitScope

@Subcomponent()
@RetrofitScope
interface RetrofitComponent {
    @Subcomponent.Builder
    interface Builder {
        fun build(): RetrofitComponent
    }

}