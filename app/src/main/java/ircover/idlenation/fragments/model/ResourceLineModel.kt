package ircover.idlenation.fragments.model

import android.content.Context
import ircover.idlenation.game.ResourceLine
import ircover.idlenation.adapters.ResourceLineAdapter
import ircover.idlenation.adapters.WorkPlaceModel

class ResourceLineModel(contextGetter: () -> Context?) {
    var onWorkPlaceSelect: ((WorkPlaceModel) -> Unit)? = null
    var resourceLine: ResourceLine? = null
    set(value) {
        field = value
        refreshAdapterItems()
    }
    val adapter: ResourceLineAdapter? by lazy {
        contextGetter()?.let { context ->
            ResourceLineAdapter(context).apply {
                onSelectListener = { onWorkPlaceSelect?.invoke(it) }
            }
        }
    }

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

    fun onInformationPanelClose() {
        adapter?.closeDetailsView()
    }
}