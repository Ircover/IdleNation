package ircover.idlenation.utils.commonFunctions

import org.apfloat.Apfloat
import org.apfloat.ApfloatMath

const val COMMON_PRECISION = 100L

enum class NumberFormat {
    Scientific
}

//возможно, для этого лучше сделать фабрику
fun createApfloat(n: Number, tenPower: Long = 0): Apfloat {
    return createApfloat(n.toDouble(), tenPower)
}
fun createApfloat(d: Double, tenPower: Long = 0): Apfloat {
    val value = Apfloat(d, COMMON_PRECISION)
    return ApfloatMath.pow(Apfloat(10.0, COMMON_PRECISION), tenPower) * value
}