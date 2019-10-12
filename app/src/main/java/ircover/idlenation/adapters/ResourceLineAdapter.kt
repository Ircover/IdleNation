package ircover.idlenation.adapters

import android.databinding.ObservableField
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import ircover.idlenation.R
import ircover.idlenation.databinding.ListElementWorkPlaceBinding
import ircover.idlenation.game.CountChangeObservable
import ircover.idlenation.game.CountSelectorModel
import ircover.idlenation.game.notifyCountChangeListeners
import ircover.idlenation.game.registerCountChangeListener
import ircover.idlenation.utils.BindingViewHolder
import ircover.idlenation.toCommonString
import ircover.idlenation.utils.Disposable
import ircover.idlenation.utils.IdleNationApplication
import org.apfloat.Apfloat

class WorkPlaceModel(val name: String,
                     startCount: Apfloat) : CountChangeObservable {
    override var count: Apfloat = startCount
        set(value) {
            field = value
            countString.notifyChange()
            notifyCountChangeListeners()
        }
    override val countChangeObservers: ArrayList<(Apfloat) -> Unit> = arrayListOf()
    val countString = object : ObservableField<String>() {
        override fun get() = count.toCommonString()
    }
    val sacrificeSelector = CountSelectorModel(IdleNationApplication.getString(R.string.sacrifice),
            this) { selectedCount ->

    }
    private var listenerDisposable: Disposable? = null

    fun registerCountChangeObserver(countChangeObservable: CountChangeObservable) {
        listenerDisposable = countChangeObservable.registerCountChangeListener { newCount ->
            count = newCount
        }
    }
}

class ResourceLineHolder(parent: ViewGroup) :
        BindingViewHolder<ListElementWorkPlaceBinding>(R.layout.list_element_work_place, parent) {
    fun setBinding(workPlace: WorkPlaceModel, position: Int, onSelect: (Int) -> Unit) {
        binding?.workPlace = workPlace
        binding?.onClick = Runnable { onSelect(position) }
    }
}

private val PAYLOAD_BACKGROUND = Any()

class ResourceLineAdapter : RecyclerView.Adapter<ResourceLineHolder>() {
    var onSelectListener: ((WorkPlaceModel) -> Unit)? = null
    private var items: Array<WorkPlaceModel> = arrayOf()
    private var selectedPosition = -1
    private val onElementSelect: (Int) -> Unit = {
        if(selectedPosition >= 0) {
            notifyItemChanged(selectedPosition, PAYLOAD_BACKGROUND)
        }
        selectedPosition = it
        notifyItemChanged(selectedPosition, PAYLOAD_BACKGROUND)
        onSelectListener?.invoke(items[selectedPosition])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResourceLineHolder =
            ResourceLineHolder(parent)

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

    fun setItems(workPlaces: Array<WorkPlaceModel>) {
        items = workPlaces
        notifyDataSetChanged()
    }

    fun clearSelection() {
        val lastSelected = selectedPosition
        selectedPosition = -1
        notifyItemChanged(lastSelected, PAYLOAD_BACKGROUND)
    }

    private fun ResourceLineHolder.refreshBackground(position: Int) {
        itemView.setBackgroundResource(if(position != selectedPosition)
            R.color.Transparent else R.color.SelectedElementBackground)
    }
}