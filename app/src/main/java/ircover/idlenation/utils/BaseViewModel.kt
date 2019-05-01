package ircover.idlenation.utils

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import ircover.idlenation.utils.livedata.TimerLiveData

abstract class BaseViewModel<T> : ViewModel() {
    val uiTimerData = TimerLiveData(16L)
    protected val liveData = MutableLiveData<T>()

    fun getValue(): T? = liveData.value
    protected fun setValue(value: T) {
        liveData.value = value
    }

    fun notifyUpdate() {
        liveData.postValue(liveData.value)
    }
}