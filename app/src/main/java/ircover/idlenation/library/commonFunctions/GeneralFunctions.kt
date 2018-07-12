package ircover.idlenation.library.commonFunctions

import android.content.Context
import android.support.annotation.ColorRes
import android.view.LayoutInflater

fun Context.getLayoutInflater() = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
fun Context.getColorFromResource(@ColorRes colorId: Int) = resources.getColor(colorId)