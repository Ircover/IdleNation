package ircover.idlenation

import ircover.idlenation.utils.getPreferencesManager
import org.apfloat.Apfloat

interface PreferencesManager {
    fun getNumberFormat(): NumberFormat
}

class PreferencesManagerImpl : PreferencesManager {
    override fun getNumberFormat(): NumberFormat {
        return NumberFormat.Scientific
    }
}

fun Apfloat.toCommonString() = toString(getPreferencesManager().getNumberFormat())