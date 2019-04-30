package ircover.idlenation.utils.livedata

import android.arch.lifecycle.MutableLiveData
import ircover.idlenation.utils.getElapsedRealtime
import java.util.Timer
import java.util.TimerTask

class TimerLiveData(private val interval: Long) : MutableLiveData<Long>() {
    private var lastRaisedTime = getElapsedRealtime()
    private var timer: Timer? = null

    override fun onActive() {
        timer = Timer().apply {
            schedule(object : TimerTask() {
                override fun run() {
                    val currentTime = getElapsedRealtime()
                    postValue(currentTime - lastRaisedTime)
                    lastRaisedTime = currentTime
                }
            }, 0L, interval)
        }
    }

    override fun onInactive() {
        timer?.cancel()
        timer = null
    }
}