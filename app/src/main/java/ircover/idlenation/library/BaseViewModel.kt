package ircover.idlenation.library

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel

abstract class BaseViewModel<T> : ViewModel() {
    protected val liveData = MutableLiveData<T>()

    fun getValue(): T? = liveData.value
    protected fun setValue(value: T) {
        liveData.value = value
    }

    fun notifyUpdate() {
        liveData.postValue(liveData.value)
    }
}