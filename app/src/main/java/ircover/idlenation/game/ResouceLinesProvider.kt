package ircover.idlenation.game

import ircover.idlenation.R
import ircover.idlenation.utils.commonFunctions.createApfloat
import ircover.idlenation.utils.IdleNationApplication

interface ResourceLinesProvider {
    val resourceLines: Array<ResourceLine>
}
class ResourceLinesProviderImpl(private val bonusWorker: BonusWorker) : ResourceLinesProvider {
    override val resourceLines: Array<ResourceLine> get() = arrayOf(
            FoodLine(bonusWorker)
    )
}

class FoodLine(bonusWorker: BonusWorker) : ResourceChain(ResourceType.Food) {
    override val workPlaces: Array<WorkPlace> = arrayOf(
            WorkPlace(1,
                    IdleNationApplication.getString(R.string.work_place_food_1),
                    resourceType,
                    baseProductionPerSecond = createApfloat(1.0),
                    bonuses = bonusWorker),
            WorkPlace(2,
                    IdleNationApplication.getString(R.string.work_place_food_2),
                    resourceType,
                    baseProductionPerSecond = createApfloat(1.0),
                    bonuses = bonusWorker),
            WorkPlace(3,
                    IdleNationApplication.getString(R.string.work_place_food_3),
                    resourceType,
                    baseProductionPerSecond = createApfloat(1.0),
                    bonuses = bonusWorker)
                .apply { addCount(createApfloat(10.0)) }
    )

}