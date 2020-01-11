package ircover.idlenation.game

import ircover.idlenation.adapters.WorkPlaceModel
import ircover.idlenation.utils.commonFunctions.createApfloat
import ircover.idlenation.utils.Printer
import ircover.idlenation.utils.commonFunctions.plus
import ircover.idlenation.utils.commonFunctions.times
import org.apfloat.Apfloat

class WorkPlace(private val id: Int,
                val name: String,
                private val resourceType: ResourceType,
                private val baseProductionPerSecond: Apfloat,
                private val bonuses: BonusWorker): CountChangeObservable {
    override var count = createApfloat(0.0)
        private set
    override val countChangeObservers: ArrayList<(Apfloat) -> Unit> = arrayListOf()

    fun getTotalProductionByTime(elapsedMillis: Long): Apfloat {
        val timeMultiplier = elapsedMillis.toDouble() / 1000
        return getTotalProductionPerSecond() * timeMultiplier
    }

    fun getTotalProductionPerSecond(): Apfloat {
        return (baseProductionPerSecond + bonuses.getProductionAdditions(resourceType)) *
                count * bonuses.getProductionMultiplier(resourceType)
    }

    fun addCount(count: Apfloat) {
        this.count += count
        notifyCountChangeListeners()
    }

    fun convertToModel(printer: Printer): WorkPlaceModel =
            WorkPlaceModel(name, count, printer).also { model ->
                model.registerCountChangeObserver(this)
            }
}