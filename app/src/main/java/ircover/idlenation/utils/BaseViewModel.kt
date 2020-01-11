package ircover.idlenation.utils

import android.arch.lifecycle.ViewModel
import ircover.idlenation.utils.livedata.TimerLiveData

abstract class BaseViewModel<T>(var model: T) : ViewModel() {
    val uiTimerData: TimerLiveData by lazy { TimerLiveData(16L, systemService) }
    lateinit var systemService: SystemService
}