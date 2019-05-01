package ircover.idlenation.fragments.model

import android.view.View
import ircover.idlenation.game.ResourceLine
import ircover.idlenation.adapters.ResourceLineAdapter
import ircover.idlenation.adapters.WorkPlaceModel
import ircover.idlenation.utils.commonFunctions.animateWidthChangeWithFading
import ircover.idlenation.utils.commonFunctions.getRealWidth

interface DetailsViewable {
    var viewToShowDetails: View?
}

class ResourceLineModel : DetailsViewable {
    var onWorkPlaceSelect: ((WorkPlaceModel) -> Unit)? = null
    var resourceLine: ResourceLine? = null
    override var viewToShowDetails: View? = null
        set(value) {
            field = value
            value?.getRealWidth { width ->
                maxDetailsWidth = width
                value.layoutParams.width = 1
                value.alpha = 0f
            }
        }
    private var maxDetailsWidth = 0
    set(value) {
        field = value
        refreshAdapterItems()
    }
    val adapter = ResourceLineAdapter().apply {
        onSelectListener = {
            onWorkPlaceSelect?.invoke(it)
            viewToShowDetails?.animateWidthChangeWithFading(maxDetailsWidth, isFading = false)
        }
    }

    private fun refreshAdapterItems() {
        resourceLine?.let { line ->
            val newItems = line.workPlaces
                    .map { it.convertToModel() }
                    .reversed()
                    .plus(line.createWorkPlaceModel())
                    .toTypedArray()
            adapter.setItems(newItems)
        }
    }

    fun onInformationPanelClose() {
        viewToShowDetails?.animateWidthChangeWithFading(1, isFading = true)
        adapter.clearSelection()
    }
}