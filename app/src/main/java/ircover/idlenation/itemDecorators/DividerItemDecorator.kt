package ircover.idlenation.itemDecorators

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.support.annotation.ColorRes
import android.support.v7.widget.RecyclerView
import android.view.View
import ircover.idlenation.R
import ircover.idlenation.library.commonFunctions.getColorFromResource

class DividerItemDecorator(context: Context,
                           @ColorRes textColorResId: Int = R.color.TextCommon) : RecyclerView.ItemDecoration() {

    private val dividerPaint = Paint()
    init {
        dividerPaint.color = context.getColorFromResource(textColorResId)
    }

    override fun getItemOffsets(outRect: Rect?, view: View?, parent: RecyclerView?, state: RecyclerView.State?) {
        outRect?.top = if(parent?.getChildAdapterPosition(view) == 0) 0 else 1
    }

    override fun onDrawOver(c: Canvas?, parent: RecyclerView?, state: RecyclerView.State?) {
        var i = 1
        while(i < parent?.childCount ?: -1) {
            val child = parent?.getChildAt(i)
            child?.let { view ->
                val viewTop = view.top.toFloat() + view.translationY
                c?.drawLine(0f, viewTop, parent.right.toFloat(), viewTop, dividerPaint)
            }
            i++
        }
    }
}