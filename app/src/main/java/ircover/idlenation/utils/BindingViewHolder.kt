package ircover.idlenation.utils

import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import ircover.idlenation.utils.commonFunctions.getLayoutInflater

open class BindingViewHolder<TBinding : ViewDataBinding> : RecyclerView.ViewHolder {
    protected val binding : TBinding?
    constructor(itemView: View) : super(itemView) {
        binding = DataBindingUtil.getBinding(itemView)
    }
    constructor(context: Context, @LayoutRes layoutId: Int, parent: ViewGroup?) :
            super(DataBindingUtil.inflate<TBinding>(
                    context.getLayoutInflater(),
                    layoutId, parent, false).root) {
        binding = DataBindingUtil.getBinding(itemView)
    }
}