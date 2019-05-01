package ircover.idlenation.utils

import android.databinding.ObservableField

open class FunctionalObservableField<T>(private val getter: () -> T, private val setter: (T) -> Unit) : ObservableField<T>() {
    override fun get() = getter()
    override fun set(value: T) = setter(value)
}

open class CalculatingObservableField<T>(getter: () -> T) : FunctionalObservableField<T>(getter, { })