package ircover.idlenation.activities.viewModels

import android.arch.lifecycle.*
import android.databinding.DataBindingUtil
import android.support.design.widget.TabLayout
import ircover.idlenation.MainActivityPage
import ircover.idlenation.R
import ircover.idlenation.databinding.ViewPageTitleBinding
import ircover.idlenation.game.ResourceLine
import ircover.idlenation.game.ResourceLinesProvider
import ircover.idlenation.game.ResourceType
import ircover.idlenation.utils.BaseViewModel
import ircover.idlenation.utils.CalculatingObservableField
import ircover.idlenation.utils.PagesFactory
import ircover.idlenation.utils.TabLayoutTitleProcessor
import ircover.idlenation.utils.commonFunctions.getLayoutInflater
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

open class MainActivityModel {
    lateinit var resourceLines: Array<ResourceLine>
    private var lastProducedMillis: Long? = null
    open fun calculateProduce(currentTimeMillis: Long) {
        lastProducedMillis?.let { last ->
            resourceLines.forEach { it.produce(currentTimeMillis - last) }
        }
        lastProducedMillis = currentTimeMillis
    }
}

class MainViewModel(mainActivityModel: MainActivityModel,
                    resourceLinesProvider: ResourceLinesProvider,
                    private val pagesFactory: PagesFactory) : BaseViewModel<MainActivityModel>(mainActivityModel) {
    init {
        model.resourceLines = resourceLinesProvider.resourceLines
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
    private val resourceLinesData = MutableLiveData<Array<ResourceLine>>().apply {
        value = model.resourceLines
    }
    val menuPages: LiveData<List<MainActivityPage>> =
            Transformations.map(resourceLinesData) { resourceLines ->
                resourceLines.map { pagesFactory.getMainActivityPage(it) }
            }
    private var isCalculating = false

    fun calculateProduceAsync() = GlobalScope.async {
        if(!isCalculating) {
            isCalculating = true
            withContext(Dispatchers.IO) {
                model.calculateProduce(systemService.getCurrentTimeMillis())
            }
            isCalculating = false
        }
    }

    fun findResourceLine(resourceType: ResourceType): LiveData<ResourceLine>
            = Transformations.map(resourceLinesData) { resourceLines ->
                resourceLines.find { it.resourceType == resourceType }
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