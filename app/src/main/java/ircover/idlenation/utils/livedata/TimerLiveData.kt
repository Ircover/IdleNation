package ircover.idlenation.utils.livedata

import android.arch.lifecycle.MutableLiveData
import android.os.SystemClock
import java.util.Timer
import java.util.TimerTask

class TimerLiveData(private val interval: Long) : MutableLiveData<Long>() {
    private var lastRaisedTime = SystemClock.elapsedRealtime()
    private var timer: Timer? = null

    override fun onActive() {
        timer = Timer().apply {
            schedule(object : TimerTask() {
                override fun run() {
                    val currentTime = SystemClock.elapsedRealtime()
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