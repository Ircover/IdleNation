package ircover.idlenation.utils

import ircover.idlenation.utils.commonFunctions.NumberFormat

interface PreferencesManager {
    fun getNumberFormat(): NumberFormat
}

class PreferencesManagerImpl : PreferencesManager {
    override fun getNumberFormat(): NumberFormat {
        return NumberFormat.Scientific
    }
}