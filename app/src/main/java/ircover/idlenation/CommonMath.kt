package ircover.idlenation

import ircover.idlenation.utils.commonFunctions.times
import org.apfloat.Apfloat
import org.apfloat.ApfloatMath
import java.util.*

const val COMMON_PRECISION = 100L

enum class NumberFormat {
    Scientific
}

fun createApfloat(d: Double, tenPower: Long = 0): Apfloat {
    val value = Apfloat(d, COMMON_PRECISION)
    return ApfloatMath.pow(Apfloat(10.0, COMMON_PRECISION), tenPower) * value
}

fun Apfloat.toString(numberFormat: NumberFormat): String = when(numberFormat) {
    NumberFormat.Scientific -> String.format(Locale.getDefault(), "%-8.4s", this)
}