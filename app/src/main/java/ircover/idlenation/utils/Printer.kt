package ircover.idlenation.utils

import ircover.idlenation.utils.commonFunctions.NumberFormat
import org.apfloat.Apfloat
import java.util.*

interface Printer {
    fun printApfloat(count: Apfloat): String
}

class PrinterImpl(private val preferencesManager: PreferencesManager) : Printer {
    override fun printApfloat(count: Apfloat) = when(preferencesManager.getNumberFormat()) {
        NumberFormat.Scientific -> String.format(Locale.getDefault(), "%-8.4s", count)
    }
}