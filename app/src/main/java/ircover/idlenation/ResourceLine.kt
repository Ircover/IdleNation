package ircover.idlenation

import ircover.idlenation.adapters.WorkPlaceModel
import ircover.idlenation.adapters.update
import ircover.idlenation.library.plus
import org.apfloat.Apfloat

abstract class ResourceLine(val resourceType: ResourceType) {
    var resourceCount: Apfloat = createApfloat(0.0)
        private set
    abstract val workPlaces: Array<WorkPlace>
    abstract fun produce(time: Long)
    private var lastCreatedModelUpdater: (((WorkPlaceModel) -> Unit) -> Unit)? = null
    private val updaters: ArrayList<(Apfloat) -> Unit> = arrayListOf()

    fun increaseResource(produced: Apfloat) {
        resourceCount += produced
        lastCreatedModelUpdater?.update(resourceCount)
        updaters.forEach { it(resourceCount) }
    }

    fun createWorkPlaceModel(): WorkPlaceModel =
            WorkPlaceModel(resourceType.getTitle(), resourceCount).apply {
                lastCreatedModelUpdater = getUpdater()
            }

    fun registerOnCountUpdate(updater: (Apfloat) -> Unit) {
        updaters += updater
    }
}