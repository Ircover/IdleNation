package ircover.idlenation.activities.viewModels

import android.support.v7.app.AppCompatActivity
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import ircover.idlenation.ResourceLine
import ircover.idlenation.game.ResourceLinesProvider
import ircover.idlenation.library.BaseViewModel

open class MainActivityModel {
    val resourceLines: Array<ResourceLine> = ResourceLinesProvider.ResourceLines
    var lastProducedMillis: Long? = null
    open fun calculateProduce() {
        val now = System.currentTimeMillis()
        lastProducedMillis?.let { last ->
            resourceLines.forEach { it.produce(now - last) }
        }
        lastProducedMillis = now
    }
}

class MainViewModel(mainActivityModel: MainActivityModel) : BaseViewModel<MainActivityModel>() {
    init {
        setValue(mainActivityModel)
    }
    constructor() : this(MainActivityModel())
    private var isCalculating = false

    fun observeResourceLines(activity: AppCompatActivity, observer: (Array<ResourceLine>) -> Unit) {
        observe(activity) { observer(it.resourceLines) }
    }

    fun calculateProduce() {
        Observable.just(Unit)
                .subscribeOn(Schedulers.computation())
                .subscribe {
                    if(!isCalculating) {
                        synchronized(this@MainViewModel) {
                            if(!isCalculating) {
                                isCalculating = true
                                getValue()?.calculateProduce()
                                isCalculating = false
                            }
                        }
                    }
                }
    }
}