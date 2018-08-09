package ircover.idlenation.library.commonFunctions

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.support.annotation.ColorRes
import android.support.v4.app.FragmentActivity
import android.view.LayoutInflater
import android.view.View

fun Context.getLayoutInflater() = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
@Suppress("DEPRECATION")
fun Context.getColorFromResource(@ColorRes colorId: Int) = resources.getColor(colorId)
inline fun <reified T: ViewModel> FragmentActivity.getViewModel() =
        ViewModelProviders.of(this).get(T::class.java)
fun View.getRealWidth(callback: (Int) -> Unit) {
    if(width > 0) {
        callback(width)
    } else {
        addOnLayoutChangeListener(object : View.OnLayoutChangeListener {
            override fun onLayoutChange(v: View,
                                        left: Int, top: Int, right: Int, bottom: Int,
                                        oldLeft: Int, oldTop: Int, oldRight: Int, oldBottom: Int) {
                callback(right - left)
                removeOnLayoutChangeListener(this)
            }
        })
    }
}