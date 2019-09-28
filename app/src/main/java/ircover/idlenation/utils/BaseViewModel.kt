package ircover.idlenation.utils

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import ircover.idlenation.utils.livedata.TimerLiveData

abstract class BaseViewModel<T>(var model: T) : ViewModel() {
    val uiTimerData = TimerLiveData(16L)
}