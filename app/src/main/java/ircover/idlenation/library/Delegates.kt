package ircover.idlenation.library

import android.app.Activity
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.annotation.LayoutRes
import kotlin.reflect.KProperty

class ActivityBinding<in TClass : Activity, out TBinding : ViewDataBinding>
(@LayoutRes private val layoutResIdGetter: () -> Int) {

    private var value: TBinding? = null

    operator fun getValue(thisRef: TClass, property: KProperty<*>): TBinding {
        value = value ?: DataBindingUtil.setContentView(thisRef, layoutResIdGetter())
        return value!!
    }

}