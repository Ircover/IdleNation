package ircover.idlenation.utils

import android.os.SystemClock

interface SystemService {
    fun getElapsedRealtime(): Long
    fun getCurrentTimeMillis(): Long
}

class SystemServiceImpl : SystemService {
    override fun getElapsedRealtime() = SystemClock.elapsedRealtime()
    override fun getCurrentTimeMillis() = System.currentTimeMillis()
}