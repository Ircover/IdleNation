package ircover.idlenation.utils

import ircover.idlenation.MainActivityPage
import ircover.idlenation.adapters.PageTitleModel
import ircover.idlenation.fragments.ResourceLinePageFragment
import ircover.idlenation.fragments.ResourceLinePageFragmentFactory
import ircover.idlenation.game.ResourceLine
import ircover.idlenation.game.registerCountChangeListener

interface PagesFactory {
    fun getMainActivityPage(resourceLine: ResourceLine): MainActivityPage
}

class PagesFactoryImpl(private val resourceLinePageFragmentFactory: ResourceLinePageFragmentFactory,
                       private val printer: Printer) : PagesFactory {
    override fun getMainActivityPage(resourceLine: ResourceLine): MainActivityPage {
        val resourceType = resourceLine.resourceType
        val resourceCount = resourceLine.resourceCount
        return object : MainActivityPage {
            var fragment: ResourceLinePageFragment? = null
            private var listenerDisposable: Disposable? = null

            override fun getTitle(): CharSequence =
                    "${resourceType.getTitle()}: ${printer.printApfloat(resourceCount)}"

            override fun createFragment(): ResourceLinePageFragment {
                return resourceLinePageFragmentFactory.createFragmentByType(resourceType).apply {
                    fragment = this
                }
            }

            override fun getPageTitleModel(): PageTitleModel =
                    PageTitleModel(resourceCount, resourceType, printer).apply {
                        listenerDisposable = resourceLine.registerCountChangeListener { count = it }
                    }
        }
    }
}