package ircover.idlenation

import com.natpryce.hamkrest.assertion.assertThat
import ircover.idlenation.game.BonusWorker
import ircover.idlenation.library.*
import org.apfloat.Apfloat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

class WorkPlaceTest {
    @Suppress("unused")
    companion object {
        @JvmStatic
        private fun productionPerSecondProvider() = arrayOf(
                arrayOf(
                        createApfloat(500.0, 1000),
                        createApfloat(30.0, 1000),
                        createApfloat(5.0, 1000),
                        createApfloat(100.0, 1000),
                        createApfloat(1.75, 3006)
                )
        )
        @JvmStatic
        private fun productionByTimeProvider() = arrayOf(
                arrayOf(
                        createApfloat(500.0),
                        createApfloat(30.0),
                        createApfloat(5.0),
                        createApfloat(100.0),
                        700L,
                        createApfloat(1.225, 6)
                )
        )
    }

    private lateinit var bonuses: BonusWorker

    @BeforeEach
    fun setup() {
        bonuses = mock(BonusWorker::class.java)
    }

    @ParameterizedTest(name = "{4}")
    @DisplayName("Получение производства в секунду")
    @MethodSource("productionPerSecondProvider")
    fun checkGetTotalProductionPerSecond(count: Apfloat,
                                         baseProductionPerSecond: Apfloat,
                                         bonusAdd: Apfloat,
                                         bonusMultiply: Apfloat,
                                         expectedResult: Apfloat) {
        `when`(bonuses.getProductionAdditions(ResourceType.Food))
                .thenReturn(bonusAdd)
        `when`(bonuses.getProductionMultiplier(ResourceType.Food))
                .thenReturn(bonusMultiply)
        val workPlace = WorkPlace(1, "", ResourceType.Food,
                baseProductionPerSecond, bonuses)
        workPlace.addCount(count)

        assertThat(workPlace.getTotalProductionPerSecond(),
                Is nearlyEqualTo expectedResult)
    }

    @ParameterizedTest(name = "{5} за {4} миллисекунд")
    @DisplayName("Получение производства за указанное время")
    @MethodSource("productionByTimeProvider")
    fun checkGetTotalProductionByTime(count: Apfloat,
                                      baseProductionPerSecond: Apfloat,
                                      bonusAdd: Apfloat,
                                      bonusMultiply: Apfloat,
                                      timeMillis: Long,
                                      expectedResult: Apfloat) {
        `when`(bonuses.getProductionAdditions(ResourceType.Food))
                .thenReturn(bonusAdd)
        `when`(bonuses.getProductionMultiplier(ResourceType.Food))
                .thenReturn(bonusMultiply)
        val workPlace = WorkPlace(1, "", ResourceType.Food,
                baseProductionPerSecond, bonuses)
        workPlace.addCount(count)

        assertThat(workPlace.getTotalProductionByTime(timeMillis),
                Is nearlyEqualTo expectedResult)
    }

}