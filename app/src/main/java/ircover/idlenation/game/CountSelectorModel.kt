package ircover.idlenation.game

import ircover.idlenation.createApfloat
import ircover.idlenation.toCommonString
import ircover.idlenation.utils.CalculatingObservableField
import org.apfloat.Apfloat

class CountSelectorModel(countChangeObservable: CountChangeObservable,
                         private val countSelectListener: (Apfloat) -> Unit) {
    var customPercent = 0
        set(value) {
            field = value
            customButtonString.notifyChange()
        }
    val minimumButtonString = CalculatingObservableField { minimumCount.toCommonString() }
    val customButtonString = CalculatingObservableField { customCount.toCommonString() }
    val maximumButtonString = CalculatingObservableField { count.toCommonString() }

    private var count = createApfloat(0.0)
    private val countChangeDisposable =
            countChangeObservable.registerCountChangeListener { newCount ->
                count = newCount
                listOf(minimumButtonString, customButtonString, maximumButtonString).forEach {
                    it.notifyChange()
                }
            }
    private val minimumCount = createApfloat(1.0)
    private val customCount get() = count.multiply(createApfloat(customPercent.toDouble() / 100.0))

    fun onMinimumButtonClick() {
        countSelectListener(if(count < minimumCount) count else minimumCount)
    }
    fun onCustomButtonClick() {
        countSelectListener(customCount)
    }
    fun onMaximumButtonClick() {
        countSelectListener(count)
    }
    fun destroy() {
        countChangeDisposable.dispose()
    }
}