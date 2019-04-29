package ircover.idlenation

import ircover.idlenation.adapters.WorkPlaceModel
import ircover.idlenation.adapters.update
import ircover.idlenation.game.BonusWorker
import ircover.idlenation.utils.plus
import ircover.idlenation.utils.times
import org.apfloat.Apfloat

class WorkPlace(private val id: Int,
                val name: String,
                private val resourceType: ResourceType,
                private val baseProductionPerSecond: Apfloat,
                private val bonuses: BonusWorker) {
    var count = createApfloat(0.0)
        private set
    private var lastCreatedModelUpdater: (((WorkPlaceModel) -> Unit) -> Unit)? = null

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
        lastCreatedModelUpdater?.update(this.count)
    }

    fun convertToModel(): WorkPlaceModel =
            WorkPlaceModel(name, count).apply { lastCreatedModelUpdater = getUpdater() }
}