package ircover.idlenation.activities.viewModels

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.arch.lifecycle.Transformations
import android.databinding.DataBindingUtil
import android.support.design.widget.TabLayout
import ircover.idlenation.*
import ircover.idlenation.databinding.ViewPageTitleBinding
import ircover.idlenation.game.ResourceLine
import ircover.idlenation.game.ResourceLinesProvider
import ircover.idlenation.game.ResourceType
import ircover.idlenation.utils.BaseViewModel
import ircover.idlenation.utils.CalculatingObservableField
import ircover.idlenation.utils.TabLayoutTitleProcessor
import ircover.idlenation.utils.commonFunctions.getLayoutInflater
import ircover.idlenation.utils.getResourceLinesProvider
import kotlinx.coroutines.*

open class MainActivityModel {
    val resourceLines: Array<ResourceLine> = getResourceLinesProvider().resourceLines
    var lastProducedMillis: Long? = null
    open fun calculateProduce() {
        val now = System.currentTimeMillis()
        lastProducedMillis?.let { last ->
            resourceLines.forEach { it.produce(now - last) }
        }
        lastProducedMillis = now
    }
}

class MainViewModel(mainActivityModel: MainActivityModel) : BaseViewModel<MainActivityModel>(mainActivityModel) {
    @Suppress("unused")
    constructor() : this(MainActivityModel())
    val resourceLinesData = MutableLiveData<Array<ResourceLine>>().apply {
        value = model.resourceLines
    }
    val tabProcessor = CalculatingObservableField<TabLayoutTitleProcessor> {
        object : TabLayoutTitleProcessor {
            override fun process(parent: TabLayout, tab: TabLayout.Tab, position: Int) {
                val binding = DataBindingUtil.inflate<ViewPageTitleBinding>(
                        parent.getLayoutInflater(),
                        R.layout.view_page_title, parent, false
                )
                binding.model = menuPages.value?.get(position)?.getPageTitleModel()
                tab.customView = binding.root
            }
        }
    }
    val menuPages: LiveData<List<MainActivityPage>> =
            Transformations.map(resourceLinesData) { resourceLines ->
                resourceLines.map { it.convertToPage() }
            }
    private var isCalculating = false

    fun calculateProduceAsync() = GlobalScope.async {
        if(!isCalculating) {
            isCalculating = true
            withContext(Dispatchers.IO) {
                model.calculateProduce()
            }
            isCalculating = false
        }
    }

    fun findResourceLine(resourceType: ResourceType): LiveData<ResourceLine>
            = Transformations.map(resourceLinesData) {
                it.find { it.resourceType == resourceType }
            }

    fun notifyPagesViewed() {
        tabProcessor.notifyChange()
    }

    fun startCalculateProducing(lifecycleOwner: LifecycleOwner) {
        uiTimerData.observe(lifecycleOwner, Observer {
            calculateProduceAsync().start()
        })
    }
}