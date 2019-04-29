package ircover.idlenation.activities.viewModels

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Transformations
import android.databinding.DataBindingUtil
import android.support.design.widget.TabLayout
import ircover.idlenation.*
import ircover.idlenation.databinding.ViewPageTitleBinding
import ircover.idlenation.game.ResourceLinesProvider
import ircover.idlenation.utils.BaseViewModel
import ircover.idlenation.utils.CalculatingObservableField
import ircover.idlenation.utils.TabLayoutTitleProcessor
import ircover.idlenation.utils.commonFunctions.getLayoutInflater
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

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
    constructor() : this(MainActivityModel())
    val resourceLinesData: LiveData<Array<ResourceLine>> = Transformations.map(liveData) {
        it.resourceLines
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
    init {
        setValue(mainActivityModel)
    }
    private var isCalculating = false

    fun calculateProduce() {
        GlobalScope.launch {
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

    fun findResourceLine(resourceType: ResourceType): LiveData<ResourceLine>
            = Transformations.map(resourceLinesData) {
                it.find { it.resourceType == resourceType }
            }

    fun notifyPagesViewed() {
        tabProcessor.notifyChange()
    }
}