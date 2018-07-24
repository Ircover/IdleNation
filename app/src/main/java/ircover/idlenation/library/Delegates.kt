package ircover.idlenation.library

import android.app.Activity
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.annotation.LayoutRes
import kotlin.reflect.KProperty

class ActivityBinding<in TClass : Activity, out TBinding : ViewDataBinding>
(@LayoutRes private val layoutResId: Int) {

    private var value: TBinding? = null

    operator fun getValue(thisRef: TClass, property: KProperty<*>): TBinding {
        return value ?: DataBindingUtil.setContentView<TBinding>(thisRef, layoutResId)
                .apply { value = this }
    }

}