package ircover.idlenation.utils

import com.natpryce.hamkrest.MatchResult
import com.natpryce.hamkrest.Matcher
import ircover.idlenation.createApfloat
import ircover.idlenation.utils.commonFunctions.minus
import ircover.idlenation.utils.commonFunctions.times
import org.apfloat.Apfloat
import org.apfloat.ApfloatMath
import kotlin.math.absoluteValue

const val ALLOWABLE_ACCURACY = 1e-15

fun <T> contentEquals(result: Array<T>) = Matcher(result::contentEquals)
fun contentEquals(result: ByteArray) = Matcher(result::contentEquals)

val Is: Matcher<Any> = Matcher(::defaultMatcherFunction)
val that: Matcher<Any> = Is
private fun defaultMatcherFunction(value: Any) = false

infix fun <T> Matcher<T>.equalTo(excepted: T) = com.natpryce.hamkrest.equalTo(excepted)
infix fun <T> Matcher<T>.equal(matcher: Matcher<T>) = matcher
infix fun <T> Matcher<T>.sameInstanceWith(excepted: T) = com.natpryce.hamkrest.sameInstance(excepted)

infix fun Matcher<Boolean>.not(excepted: Boolean) = !excepted
infix fun <T> Matcher<T>.not(excepted: Matcher<T>) = object : Matcher<T> {
    override val description: String
        get() = excepted.negatedDescription

    override fun invoke(actual: T) = excepted.not().invoke(actual)
}

infix fun <T> Matcher<Array<T>>.contentExactlyEquals(excepted: Array<T>) = contentEquals(excepted)
infix fun Matcher<ByteArray>.contentExactlyEquals(excepted: ByteArray) = contentEquals(excepted)

infix fun Matcher<Apfloat>.nearlyEqualTo(excepted: Apfloat) =
        excepted.plusOrMinus(excepted * ALLOWABLE_ACCURACY)

infix fun Double.plusOrMinus(tolerance: Double) = object : Matcher<Double> {
    override val description: String
        get() = "is equal to ${this@plusOrMinus} plus or minus $tolerance"

    override fun invoke(actual: Double) = if(this@plusOrMinus.equalsPlusOrMinus(actual, tolerance)) {
        MatchResult.Match
    } else {
        MatchResult.Mismatch("${this@plusOrMinus} is not equal $actual plus or minus $tolerance")
    }
}

private fun Double.equalsPlusOrMinus(actual: Double, tolerance: Double): Boolean =
        (this - actual).absoluteValue < tolerance

infix fun Apfloat.plusOrMinus(tolerance: Double) = plusOrMinus(createApfloat(tolerance))
infix fun Apfloat.plusOrMinus(tolerance: Apfloat) = object : Matcher<Apfloat> {
    override val description: String
        get() = "is equal to ${this@plusOrMinus} plus or minus $tolerance"

    override fun invoke(actual: Apfloat) = if(this@plusOrMinus.equalsPlusOrMinus(actual, tolerance)) {
        MatchResult.Match
    } else {
        MatchResult.Mismatch("${this@plusOrMinus} is not equal $actual plus or minus $tolerance")
    }
}

private fun Apfloat.equalsPlusOrMinus(actual: Apfloat, tolerance: Double): Boolean =
        equalsPlusOrMinus(actual, Apfloat(tolerance))
private fun Apfloat.equalsPlusOrMinus(actual: Apfloat, tolerance: Apfloat): Boolean =
        ApfloatMath.abs(this - actual) < tolerance