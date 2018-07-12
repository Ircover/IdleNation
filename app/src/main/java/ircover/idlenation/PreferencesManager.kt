package ircover.idlenation

import org.apfloat.Apfloat

class PreferencesManager {
    companion object {
        fun getNumberFormat(): NumberFormat {
            return NumberFormat.Scientific
        }
    }
}

fun Apfloat.toCommonString() = toString(PreferencesManager.getNumberFormat())