package ircover.idlenation.adapters

import android.content.Context
import android.databinding.ObservableField
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import ircover.idlenation.R
import ircover.idlenation.databinding.ListElementWorkPlaceBinding
import ircover.idlenation.library.BindingViewHolder
import ircover.idlenation.library.commonFunctions.animateWidthChange
import ircover.idlenation.toCommonString
import org.apfloat.Apfloat

class WorkPlaceModel(val name: String,
                     var count: Apfloat) {
    val countString = object : ObservableField<String>() {
        override fun get() = count.toCommonString()
    }
    fun getUpdater(): ((WorkPlaceModel) -> Unit) -> Unit = {
        it(this)
        countString.notifyChange()
    }
}

fun (((WorkPlaceModel) -> Unit) -> Unit).update(newCount: Apfloat) {
    invoke { it.count = newCount }
}

class ResourceLineHolder(context: Context, parent: ViewGroup?) :
        BindingViewHolder<ListElementWorkPlaceBinding>(context, R.layout.list_element_work_place, parent) {
    fun setBinding(workPlace: WorkPlaceModel, position: Int, onSelect: (Int) -> Unit) {
        binding?.workPlace = workPlace
        binding?.onClick = Runnable { onSelect(position) }
    }
}

private val PAYLOAD_BACKGROUND = Any()

class ResourceLineAdapter(private val context: Context) : RecyclerView.Adapter<ResourceLineHolder>() {
    private var items: Array<WorkPlaceModel> = arrayOf()
    private var maxDetailsWidth = 0
    var viewToShowDetails: View? = null
    set(value) {
        field = value
        maxDetailsWidth = value?.width ?: 0
        value?.layoutParams?.width = 1
    }
    private var selectedPosition = -1
    private val onElementSelect: (Int) -> Unit = {
        if(selectedPosition >= 0) {
            notifyItemChanged(selectedPosition, PAYLOAD_BACKGROUND)
        }
        selectedPosition = it
        notifyItemChanged(it, PAYLOAD_BACKGROUND)
        viewToShowDetails?.animateWidthChange(maxDetailsWidth)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResourceLineHolder =
            ResourceLineHolder(context, parent)

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ResourceLineHolder, position: Int, payloads: MutableList<Any>) {
        if(payloads.contains(PAYLOAD_BACKGROUND)) {
            holder.refreshBackground(position)
        } else {
            super.onBindViewHolder(holder, position, payloads)
        }
    }

    override fun onBindViewHolder(holder: ResourceLineHolder, position: Int) {
        holder.setBinding(items[position], position, onElementSelect)
        holder.refreshBackground(position)
    }

    private fun ResourceLineHolder.refreshBackground(position: Int) {
        itemView.setBackgroundResource(if(position != selectedPosition)
            R.color.Transparent else R.color.SelectedElementBackground)
    }

    fun setItems(workPlaces: Array<WorkPlaceModel>) {
        items = workPlaces
        notifyDataSetChanged()
    }

    fun closeDetailsView() {
        val lastSelected = selectedPosition
        selectedPosition = -1
        notifyItemChanged(lastSelected, PAYLOAD_BACKGROUND)
        viewToShowDetails?.animateWidthChange(1)
    }
}