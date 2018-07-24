package ircover.idlenation.library.commonFunctions

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.support.annotation.ColorRes
import android.support.v4.app.FragmentActivity
import android.view.LayoutInflater

fun Context.getLayoutInflater() = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
fun Context.getColorFromResource(@ColorRes colorId: Int) = resources.getColor(colorId)
inline fun <reified T: ViewModel> FragmentActivity.getViewModel() =
        ViewModelProviders.of(this).get(T::class.java)