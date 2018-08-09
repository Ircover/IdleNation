package ircover.idlenation.library.commonFunctions

import android.animation.ValueAnimator
import android.view.View
import ircover.idlenation.R
import ircover.idlenation.library.IdleNationApplication

fun View.animateWidthChangeWithFading(widthEnd: Int, isFading: Boolean) {
    val widthStart = width
    val widthAnimator = ValueAnimator.ofInt(widthStart, widthEnd)
            .setDuration(IdleNationApplication.getInteger(R.integer.duration_animations).toLong())
    widthAnimator.addUpdateListener { anim ->
        val lp = layoutParams
        lp.width = anim.animatedValue as Int
        layoutParams = lp
    }
    val alphaEnd = if(isFading) 0f else 1f
    val fadingAnimator = ValueAnimator.ofFloat(alpha, alphaEnd)
            .setDuration(IdleNationApplication.getInteger(R.integer.duration_animations_short).toLong())
    if(!isFading) {
        fadingAnimator.startDelay = widthAnimator.duration - fadingAnimator.duration
    }
    fadingAnimator.addUpdateListener { anim ->
        alpha = anim.animatedValue as Float
    }
    widthAnimator.start()
    fadingAnimator.start()
}