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

class ResourceLineAdapter(private val context: Context) : RecyclerView.Adapter<ResourceLineHolder>() {
    private var items: Array<WorkPlaceModel> = arrayOf()
    var maxDetailsWidth = 0
    var viewToShowDetails: View? = null
    set(value) {
        field = value
        maxDetailsWidth = value?.width ?: 0
        value?.layoutParams?.width = 1
    }
    private var selectedPosition = -1
    private val onElementSelect: (Int) -> Unit = {
        if(selectedPosition >= 0) {
            notifyItemChanged(selectedPosition)
        }
        selectedPosition = it
        notifyItemChanged(it)
        viewToShowDetails?.animateWidthChange(maxDetailsWidth)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResourceLineHolder =
            ResourceLineHolder(context, parent)

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ResourceLineHolder, position: Int) {
        holder.setBinding(items[position], position, onElementSelect)
        holder.itemView.setBackgroundResource(if(position != selectedPosition)
            R.color.Transparent else R.color.SelectedElementBackground)
    }

    fun setItems(workPlaces: Array<WorkPlaceModel>) {
        items = workPlaces
        notifyDataSetChanged()
    }

    fun closeDetailsView() {
        viewToShowDetails?.animateWidthChange(1)
    }
}