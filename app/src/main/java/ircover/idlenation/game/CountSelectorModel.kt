package ircover.idlenation.game

import ircover.idlenation.utils.commonFunctions.createApfloat
import ircover.idlenation.utils.CalculatingObservableField
import ircover.idlenation.utils.Printer
import ircover.idlenation.utils.helpers.toViewVisibility
import org.apfloat.Apfloat

private const val PERCENT_MINIMUM = 1
private const val PERCENT_MAXIMUM = 100

class CountSelectorModel(val title: String,
                         countChangeObservable: CountChangeObservable,
                         private val printer: Printer,
                         private val countSelectListener: (Apfloat) -> Unit) {
    val progressMax = PERCENT_MAXIMUM - PERCENT_MINIMUM
    var customPercent = 0
        set(value) {
            field = value
            customPercentApfloat = createApfloat((value + PERCENT_MINIMUM).toDouble() / 100.0)
            customButtonString.notifyChange()
            customButtonVisibility.notifyChange()
        }
    val minimumButtonString = CalculatingObservableField { printer.printApfloat(minimumCount) }
    val customButtonString = CalculatingObservableField { printer.printApfloat(customCount) }
    val maximumButtonString = CalculatingObservableField { printer.printApfloat(count) }
    private val minimumCount = createApfloat(1.0)
    var customButtonVisibility = CalculatingObservableField { (customCount != minimumCount).toViewVisibility() }

    private var customPercentApfloat = createApfloat(PERCENT_MINIMUM, -2)
    private var count = createApfloat(0.0)
    private val countChangeDisposable =
            countChangeObservable.registerCountChangeListener { newCount ->
                count = newCount
                customButtonVisibility.notifyChange()
                listOf(minimumButtonString, customButtonString, maximumButtonString).forEach {
                    it.notifyChange()
                }
            }
    private val customCount get() = count.multiply(customPercentApfloat).let {
        val minimumValue = createApfloat(1.0)
        if(it != createApfloat(0.0) && it < minimumValue) {
            minimumValue
        } else {
            it
        }
    }

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