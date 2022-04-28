package kg.mobile.coins

import android.app.Application
import android.content.Context
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kg.mobile.coins.dagger.AppComponent
import kg.mobile.coins.dagger.AppComponentDependencies
import kg.mobile.coins.dagger.DaggerAppComponent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class App: Application(), AppComponentDependencies {

    lateinit var appComponent: AppComponent
    private set

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent
              .builder()
              .bindContext(this)
              .build()
    }

    override val context: Context
        get() = this

}

val Context.appComponent : AppComponent
get() = when (this) {
    is App -> appComponent
    else -> this.applicationContext.appComponent
}

inline fun <T> Fragment.bind(
    source: Flow<T>,
    crossinline action: (T) -> Unit
) {
    source.onEach { action.invoke(it) }
        .launchIn(viewLifecycleOwner.lifecycleScope)
}