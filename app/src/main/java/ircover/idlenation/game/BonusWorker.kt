package ircover.idlenation.game

import ircover.idlenation.ResourceType
import ircover.idlenation.createApfloat
import org.apfloat.Apfloat

interface BonusWorker {
    fun getProductionMultiplier(resourceType: ResourceType): Apfloat
    fun getProductionAdditions(resourceType: ResourceType): Apfloat

    companion object {
        val Instance = object : BonusWorker {
            override fun getProductionMultiplier(resourceType: ResourceType): Apfloat {
                return createApfloat(1.0)
            }

            override fun getProductionAdditions(resourceType: ResourceType): Apfloat {
                return createApfloat(0.0)
            }

        }
    }
}