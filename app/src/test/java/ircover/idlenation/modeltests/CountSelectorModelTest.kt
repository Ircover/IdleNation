package ircover.idlenation.modeltests

import com.natpryce.hamkrest.assertion.assertThat
import ircover.idlenation.createApfloat
import ircover.idlenation.game.CountChangeObservable
import ircover.idlenation.game.CountSelectorModel
import ircover.idlenation.utils.Is
import ircover.idlenation.utils.assertThat
import ircover.idlenation.utils.nearlyEqualTo
import org.apfloat.Apfloat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class CountSelectorModelTest {

    @ParameterizedTest(name = "Для количества {0} должен вернуть {1}")
    @CsvSource(
            "0.0, 0.0",
            "1.0, 1.0",
            "9999999.0, 1.0")
    fun minimumClick_CalledListener(count: Double, expectedDoubleValue: Double) {
        assertModelButtonClickResult(count, expectedDoubleValue) {
            onMinimumButtonClick()
        }
    }

    @ParameterizedTest(name = "Для количества {0} должен вернуть {1}")
    @CsvSource(
            "0.0, 0.0",
            "1.0, 1.0",
            "9999999.0, 9999999.0")
    fun maximumClick_CalledListener(count: Double, expectedDoubleValue: Double) {
        assertModelButtonClickResult(count, expectedDoubleValue) {
            onMaximumButtonClick()
        }
    }

    @ParameterizedTest(name = "Для количества {0} и прогресса {1} должен вернуть {2}")
    @CsvSource(
            "0.0, 50, 0.0",
            //"1.0, 50, 1.0",
            "5000.0, 50, 2500.0")
    fun customClick_CalledListener(count: Double, percent: Int, expectedDoubleValue: Double) {
        assertModelButtonClickResult(count, expectedDoubleValue) {
            customPercent = percent
            onCustomButtonClick()
        }
    }

    private fun assertModelButtonClickResult(count: Double, expectedDoubleValue: Double,
                                             clickInvoker: CountSelectorModel.() -> Unit) {
        val expectedValue = createApfloat(expectedDoubleValue)
        var isListenerCalled = false
        val countSelectListener = { calledCount: Apfloat ->
            assertThat(calledCount, Is nearlyEqualTo expectedValue,
                    "Возвращено неверное количество")
            isListenerCalled = true
        }
        val model = createModel(createApfloat(count), countSelectListener)

        model.clickInvoker()

        assertThat(isListenerCalled, "Результат не был возвращён")
    }

    private fun createModel(count: Apfloat,
                            countSelectListener: ((Apfloat) -> Unit)? = null) =
            createModel(createCountChangeObservable(count), countSelectListener)
    private fun createModel(countChangeObservable: CountChangeObservable,
                            countSelectListener: ((Apfloat) -> Unit)? = null) =
            CountSelectorModel(countChangeObservable,
                    countSelectListener ?: { })
    private fun createCountChangeObservable(count: Apfloat) = object : CountChangeObservable {
        override val countChangeObservers: ArrayList<(Apfloat) -> Unit> =
                arrayListOf()
        override val count: Apfloat = count
    }
}