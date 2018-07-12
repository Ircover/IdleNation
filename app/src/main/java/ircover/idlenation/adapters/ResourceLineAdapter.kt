package ircover.idlenation.adapters

import android.content.Context
import android.databinding.ObservableField
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import ircover.idlenation.PreferencesManager
import ircover.idlenation.R
import ircover.idlenation.databinding.ListElementWorkPlaceBinding
import ircover.idlenation.library.BindingViewHolder
import ircover.idlenation.toCommonString
import ircover.idlenation.toString
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
    fun setBinding(workPlace: WorkPlaceModel) {
        binding?.workPlace = workPlace
    }
}

class ResourceLineAdapter(private val context: Context) : RecyclerView.Adapter<ResourceLineHolder>() {
    private var items: Array<WorkPlaceModel> = arrayOf()

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ResourceLineHolder =
            ResourceLineHolder(context, parent)

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ResourceLineHolder?, position: Int) {
        holder?.setBinding(items[position])
    }

    fun setItems(workPlaces: Array<WorkPlaceModel>) {
        items = workPlaces
        notifyDataSetChanged()
    }
}