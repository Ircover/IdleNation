package ircover.idlenation.game

import ircover.idlenation.utils.Disposable
import org.apfloat.Apfloat

interface CountChangeObservable {
    val countChangeObservers: ArrayList<(Apfloat) -> Unit>
    val count: Apfloat
}

fun CountChangeObservable.registerCountChangeListener(listener: (Apfloat) -> Unit): Disposable {
    countChangeObservers.add(listener)
    listener.invoke(count)
    return object : Disposable {
        override fun dispose() {
            unregisterCountChangeListener(listener)
        }
    }
}

fun CountChangeObservable.unregisterCountChangeListener(listener: (Apfloat) -> Unit) {
    countChangeObservers.remove(listener)
}

fun CountChangeObservable.notifyCountChangeListeners() {
    countChangeObservers.forEach { it.invoke(count) }
}