package ircover.idlenation.library

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.OnLifecycleEvent
import android.databinding.ObservableField
import kotlin.reflect.KMutableProperty1

class BiotechLifecycleObserver(val onPauseObserver: () -> Unit) : LifecycleObserver {
    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPauseListener() {
        onPauseObserver()
    }
}

class ObservableFromPropertyField<TIn, TOut>(private val valueGetter: (TIn) -> TOut,
                                             private val valueSetter: (TIn, TOut) -> Unit,
                                             private val objectGetter: () -> TIn) : ObservableField<TOut>() {
    constructor(property: KMutableProperty1<TIn, TOut>, objectGetter: () -> TIn) :
            this(property::get, property::set, objectGetter)
    private val observers: ArrayList<(TOut) -> Unit> = arrayListOf()

    override fun get() = valueGetter(objectGetter.invoke())
    override fun set(value: TOut) {
        valueSetter(objectGetter.invoke(), value)
        observers.forEach { it(value) }
    }

    private fun addObserver(observer: (TOut) -> Unit) {
        observers.add(observer)
        observer.invoke(get())
    }

    fun addObserver(lifecycleOwner: LifecycleOwner, observer: (TOut) -> Unit) {
        lifecycleOwner.lifecycle.addObserver(BiotechLifecycleObserver {
            observers.remove(observer)
        })
        addObserver(observer)
    }

    fun clear() {
        observers.clear()
    }

    fun <TNew> transform(getterTransform: (TOut) -> TNew) = transform(getterTransform) { _, _ -> }
    fun <TNew> transform(getterTransform: (TOut) -> TNew, setterTransform: (TIn, TNew) -> Unit): ObservableFromPropertyField<TIn, TNew> {
        val result = ObservableFromPropertyField({ getterTransform(valueGetter(it)) },
            { it: TIn, newValue: TNew -> setterTransform(it, newValue) },
            objectGetter)
        observers.add { result.notifyChange() }
        return result
    }
}

fun <TIn, TOut> ObservableFromPropertyField<TIn, TOut?>.transformLoadable(loadItemsAction: () -> Unit,
                                                                          emptyResultCreator: () -> TOut) =
    transform({ objects ->
        if(objects == null) {
            loadItemsAction()
            emptyResultCreator()
        } else {
            objects
        }
    }, { _, objects: TOut -> set(objects)})

open class FunctionalObservableField<T>(private val getter: () -> T, private val setter: (T) -> Unit) : ObservableField<T>() {
    override fun get() = getter()
    override fun set(value: T) = setter(value)
}

open class CalculatingObservableField<T>(getter: () -> T) : FunctionalObservableField<T>(getter, { })

class ObservableEvent<T> {
    private val observers: ArrayList<(T) -> Boolean> = arrayListOf()
    private var publishedEvent: T? = null

    private fun addObserver(observer: (T) -> Boolean) {
        observers.add(observer)
        publishedEvent?.let { event ->
            if(observer.invoke(event)) {
                publishedEvent = null
            }
        }
    }

    fun addObserver(lifecycleOwner: LifecycleOwner, observer: (T) -> Boolean) {
        lifecycleOwner.lifecycle.addObserver(BiotechLifecycleObserver {
            observers.remove(observer)
        })
        addObserver(observer)
    }

    fun publishEvent(event: T) {
        publishedEvent = event
        if(observers.any { it.invoke(event) }) {
            publishedEvent = null
        }
    }

    fun repeatEvent() {
        publishedEvent?.let { event ->
            publishEvent(event)
        }
    }
}