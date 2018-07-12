package ircover.idlenation.game

import ircover.idlenation.*
import ircover.idlenation.library.IdleNationApplication

class ResourceLinesProvider {
    companion object {
        val ResourceLines: Array<ResourceLine> get() = arrayOf(
                FoodLine()
        )
    }
}

class FoodLine : ResourceChain(ResourceType.Food) {
    override val workPlaces: Array<WorkPlace> = arrayOf(
            WorkPlace(1,
                    IdleNationApplication.getString(R.string.work_place_food_1),
                    resourceType,
                    baseProductionPerSecond = createApfloat(1.0),
                    bonuses = BonusWorker.Instance),
            WorkPlace(2,
                    IdleNationApplication.getString(R.string.work_place_food_2),
                    resourceType,
                    baseProductionPerSecond = createApfloat(1.0),
                    bonuses = BonusWorker.Instance),
            WorkPlace(3,
                    IdleNationApplication.getString(R.string.work_place_food_3),
                    resourceType,
                    baseProductionPerSecond = createApfloat(1.0),
                    bonuses = BonusWorker.Instance)
                .apply { addCount(createApfloat(10.0)) }
    )

}