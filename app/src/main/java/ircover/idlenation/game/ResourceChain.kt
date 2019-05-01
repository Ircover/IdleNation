package ircover.idlenation.game

abstract class ResourceChain(resourceType: ResourceType) : ResourceLine(resourceType) {
    override fun produce(time: Long) {
        workPlaces.forEachIndexed { index, workPlace ->
            val produced = workPlace.getTotalProductionByTime(time)
            if(index == 0) {
                increaseResource(produced)
            } else {
                workPlaces[index - 1].addCount(produced)
            }
        }
    }
}