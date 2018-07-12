package ircover.idlenation.library

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModel
import android.content.Context
import android.support.v7.app.AppCompatActivity

abstract class BaseViewModel<T> : ViewModel() {
    private val liveData = MutableLiveData<T>()
    protected fun observe(activity: AppCompatActivity, observer: (T) -> Unit) {
        liveData.observe(activity, Observer<T> { it?.let(observer) })
    }

    fun getValue(): T? = liveData.value
    protected fun setValue(value: T) {
        liveData.value = value
    }

    fun notifyUpdate() {
        liveData.postValue(liveData.value)
    }
}