package ircover.idlenation.library.commonFunctions

import android.animation.ValueAnimator
import android.view.View
import android.view.View.MeasureSpec
import ircover.idlenation.R
import ircover.idlenation.library.IdleNationApplication

fun View.animateWidthChange(widthEnd: Int) {
    val widthStart = width
    val animator = ValueAnimator.ofInt(widthStart, widthEnd)
            .setDuration(IdleNationApplication.getInteger(R.integer.duration_animations).toLong())
    animator.addUpdateListener { anim ->
        val lp = layoutParams
        lp.width = anim.animatedValue as Int
        layoutParams = lp
    }
    animator.start()
}