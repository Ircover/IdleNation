package ircover.idlenation.utils.livedata

import android.arch.lifecycle.MutableLiveData
import ircover.idlenation.utils.SystemService
import java.util.*

class TimerLiveData(private val interval: Long,
                    private val systemService: SystemService) : MutableLiveData<Long>() {
    private var lastRaisedTime = systemService.getElapsedRealtime()
    private var timer: Timer? = null

    override fun onActive() {
        timer = Timer().apply {
            schedule(object : TimerTask() {
                override fun run() {
                    val currentTime = systemService.getElapsedRealtime()
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