package ircover.idlenation.fragments.model

import android.view.View
import ircover.idlenation.adapters.ResourceLineAdapter
import ircover.idlenation.adapters.WorkPlaceModel
import ircover.idlenation.game.ResourceLine
import ircover.idlenation.utils.Printer
import ircover.idlenation.utils.commonFunctions.animateWidthChangeWithFading
import ircover.idlenation.utils.commonFunctions.getRealWidth

interface DetailsViewable {
    var viewToShowDetails: View?
}

class ResourceLineModel(private val printer: Printer) : DetailsViewable {
    var onWorkPlaceSelect: ((WorkPlaceModel) -> Unit)? = null
    var resourceLine: ResourceLine? = null
        set(value) {
            field = value
            refreshAdapterItems()
        }
    override var viewToShowDetails: View? = null //TODO: этому здесь не место
        set(value) {
            field = value
            value?.getRealWidth { width ->
                maxDetailsWidth = width
                value.layoutParams.width = 1
                value.alpha = 0f
            }
        }
    private var maxDetailsWidth = 0
    val adapter = ResourceLineAdapter().apply {
        onSelectListener = {
            onWorkPlaceSelect?.invoke(it)
            viewToShowDetails?.animateWidthChangeWithFading(maxDetailsWidth, isFading = false)
        }
    }

    private fun refreshAdapterItems() {
        resourceLine?.let { line ->
            val newItems = line.workPlaces
                    .map { it.convertToModel(printer) }
                    .reversed()
                    .plus(line.createWorkPlaceModel(printer))
                    .toTypedArray()
            adapter.setItems(newItems)
        }
    }

    fun onInformationPanelClose() {
        viewToShowDetails?.animateWidthChangeWithFading(1, isFading = true)
        adapter.clearSelection()
    }
}