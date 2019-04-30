package ircover.idlenation

import com.natpryce.hamkrest.assertion.assertThat
import ircover.idlenation.game.BonusWorker
import ircover.idlenation.game.ResourceChain
import ircover.idlenation.game.ResourceType
import ircover.idlenation.game.WorkPlace
import ircover.idlenation.utils.Is
import ircover.idlenation.utils.equalTo
import ircover.idlenation.utils.nearlyEqualTo
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mockito
import org.mockito.Mockito.*

class ResourceChainTest {
    private lateinit var chain: ResourceChain

    @BeforeEach
    fun setup() {
        chain = Mockito.mock(ResourceChain::class.java,
                    withSettings().useConstructor(ResourceType.Food))
    }

    @Test
    @DisplayName("Производство в цепочке")
    fun checkProduce() {
        val bonuses = Mockito.mock(BonusWorker::class.java)
        val wp1 = WorkPlace(1, "", ResourceType.Food,
                baseProductionPerSecond = createApfloat(3.0, 3),
                bonuses = bonuses)
        val wp2 = WorkPlace(1, "", ResourceType.Food,
                baseProductionPerSecond = createApfloat(3.0, 3),
                bonuses = bonuses)
        val wp3 = WorkPlace(1, "", ResourceType.Food,
                baseProductionPerSecond = createApfloat(3.0, 3),
                bonuses = bonuses)
        val count = createApfloat(5.0, 10)
        listOf(wp1, wp2, wp3).forEach { it.addCount(count) }

        `when`(chain.workPlaces).thenReturn(arrayOf(wp1, wp2, wp3))
        `when`(chain.produce(ArgumentMatchers.anyLong()))
                .thenCallRealMethod()
        `when`(bonuses.getProductionAdditions(ResourceType.Food))
                .thenReturn(createApfloat(1.0, 3))
        `when`(bonuses.getProductionMultiplier(ResourceType.Food))
                .thenReturn(createApfloat(2.0, 2))

        chain.produce(750L)
        assertThat(chain.workPlaces[2].count, Is equalTo createApfloat(5.0, 10))
        assertThat(chain.workPlaces[1].count, Is nearlyEqualTo createApfloat(3000005.0, 10))
        assertThat(chain.workPlaces[0].count, Is nearlyEqualTo createApfloat(3000005.0, 10))
        assertThat(chain.resourceCount, Is nearlyEqualTo createApfloat(3.0, 16))

        chain.produce(500L)
        assertThat(chain.workPlaces[2].count, Is equalTo createApfloat(5.0, 10))
        assertThat(chain.workPlaces[1].count, Is nearlyEqualTo createApfloat(5000005.0, 10))
        assertThat(chain.workPlaces[0].count, Is nearlyEqualTo createApfloat(1200005000005.0, 10))
        assertThat(chain.resourceCount, Is nearlyEqualTo createApfloat(1200005.0, 16))
    }
}