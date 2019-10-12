package ircover.idlenation.utils.helpers

import android.view.View

fun Boolean.toViewVisibility() = if(this) {
    View.VISIBLE
} else {
    View.GONE
}