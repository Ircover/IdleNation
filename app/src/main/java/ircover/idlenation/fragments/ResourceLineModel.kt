package ircover.idlenation.fragments

import android.content.Context
import ircover.idlenation.ResourceLine
import ircover.idlenation.adapters.ResourceLineAdapter
import ircover.idlenation.adapters.WorkPlaceModel

class ResourceLineModel(contextGetter: () -> Context?) {
    var resourceLine: ResourceLine? = null
    set(value) {
        field = value
        refreshAdapterItems()
    }
    val adapter: ResourceLineAdapter? by lazy { contextGetter()?.let { ResourceLineAdapter(it) } }

    private fun refreshAdapterItems() {
        resourceLine?.let { line ->
            val newItems = line.workPlaces
                    .map { it.convertToModel() }
                    .reversed()
                    .plus(line.createWorkPlaceModel())
                    .toTypedArray()
            adapter?.setItems(newItems)
        }
    }
}