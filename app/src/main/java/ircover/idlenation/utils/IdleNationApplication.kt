package ircover.idlenation.utils

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.support.annotation.IntegerRes
import android.support.annotation.StringRes

class IdleNationApplication : Application() {

    companion object {
        @SuppressLint("StaticFieldLeak")
        private var context: Context? = null
        fun getApplicationContext() = context
        fun getString(@StringRes resId: Int) = context?.getString(resId) ?: ""
        fun getInteger(@IntegerRes resId: Int) = context?.resources?.getInteger(resId) ?: 0
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }
}