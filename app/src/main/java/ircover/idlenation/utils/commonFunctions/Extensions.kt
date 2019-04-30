package ircover.idlenation.utils.commonFunctions

import ircover.idlenation.COMMON_PRECISION
import org.apfloat.Apfloat

operator fun Apfloat.plus(a: Apfloat): Apfloat = add(a)
operator fun Apfloat.minus(a: Apfloat): Apfloat = add(a.negate())
operator fun Apfloat.times(a: Apfloat): Apfloat = multiply(a)
operator fun Apfloat.times(d: Double): Apfloat = multiply(Apfloat(d, COMMON_PRECISION))