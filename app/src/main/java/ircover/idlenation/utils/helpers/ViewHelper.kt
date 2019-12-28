package ircover.idlenation.utils.helpers

import android.view.View

enum class HidingMode(val visibility: Int) {
    GONE(View.GONE),
    INVISIBLE(View.INVISIBLE)
}

fun Boolean.toViewVisibility(hidingMode: HidingMode = HidingMode.GONE) = if(this) {
    View.VISIBLE
} else {
    hidingMode.visibility
}