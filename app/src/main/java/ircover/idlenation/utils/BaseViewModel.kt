package ircover.idlenation.utils

import android.arch.lifecycle.ViewModel
import ircover.idlenation.utils.livedata.TimerLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel<T>(var model: T,
                                protected val systemService: SystemService,
                                coroutineContext: CoroutineContext) : ViewModel() {
    val uiTimerData: TimerLiveData by lazy { TimerLiveData(16L, systemService) }
    protected val scope = CoroutineScope(coroutineContext)

    override fun onCleared() {
        scope.cancel()
    }
}