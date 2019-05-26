package ircover.idlenation.game

import ircover.idlenation.*
import ircover.idlenation.utils.IdleNationApplication
import ircover.idlenation.utils.getBonusWorker

interface ResourceLinesProvider {
    val resourceLines: Array<ResourceLine>
}
class ResourceLinesProviderImpl : ResourceLinesProvider {
    override val resourceLines: Array<ResourceLine> get() = arrayOf(
            FoodLine()
    )
}

class FoodLine : ResourceChain(ResourceType.Food) {
    override val workPlaces: Array<WorkPlace> = arrayOf(
            WorkPlace(1,
                    IdleNationApplication.getString(R.string.work_place_food_1),
                    resourceType,
                    baseProductionPerSecond = createApfloat(1.0),
                    bonuses = getBonusWorker()),
            WorkPlace(2,
                    IdleNationApplication.getString(R.string.work_place_food_2),
                    resourceType,
                    baseProductionPerSecond = createApfloat(1.0),
                    bonuses = getBonusWorker()),
            WorkPlace(3,
                    IdleNationApplication.getString(R.string.work_place_food_3),
                    resourceType,
                    baseProductionPerSecond = createApfloat(1.0),
                    bonuses = getBonusWorker())
                .apply { addCount(createApfloat(10.0)) }
    )

}