package ircover.idlenation.game

import ircover.idlenation.adapters.WorkPlaceModel
import ircover.idlenation.createApfloat
import ircover.idlenation.utils.commonFunctions.plus
import org.apfloat.Apfloat

abstract class ResourceLine(val resourceType: ResourceType): CountChangeObservable {
    var resourceCount: Apfloat = createApfloat(0.0)
        private set
    override val count get() = resourceCount
    override val countChangeObservers: ArrayList<(Apfloat) -> Unit> = arrayListOf()
    abstract val workPlaces: Array<WorkPlace>
    abstract fun produce(time: Long)

    fun increaseResource(produced: Apfloat) {
        resourceCount += produced
        notifyCountChangeListeners()
    }

    fun createWorkPlaceModel(): WorkPlaceModel =
            WorkPlaceModel(resourceType.getTitle(), resourceCount).also { model ->
                model.registerCountChangeObserver(this)
            }
}