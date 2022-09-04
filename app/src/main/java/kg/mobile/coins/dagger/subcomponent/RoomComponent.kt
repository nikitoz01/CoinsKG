package kg.mobile.coins.dagger.subcomponent

import dagger.Subcomponent
import kg.mobile.coins.dagger.RoomScope

@Subcomponent()
@RoomScope
interface RoomComponent {
    @Subcomponent.Builder
    interface Builder{
        fun build():RoomComponent
    }

}