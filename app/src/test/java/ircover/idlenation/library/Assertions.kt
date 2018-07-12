package ircover.idlenation.library

import com.natpryce.hamkrest.Matcher
import kotlin.test.assertTrue

fun <T> assertThat(actual: T, matcher: Matcher<T>, message: String) =
        com.natpryce.hamkrest.assertion.assertThat(message, actual, matcher)
fun assertThat(actual: Boolean, message: String? = null) = assertTrue(actual, message)
fun <T> assertThat(actual: T, matcher: (T) -> Boolean, message: String? = null) = assertThat(matcher(actual), message)
fun assert(actual: Boolean, message: String? = null) = assertThat(actual, message)