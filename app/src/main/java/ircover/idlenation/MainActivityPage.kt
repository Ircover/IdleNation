package ircover.idlenation

import android.support.v4.app.Fragment
import ircover.idlenation.adapters.PageTitleModel
import ircover.idlenation.fragments.ResourceLinePageFragment
import ircover.idlenation.game.ResourceLine
import ircover.idlenation.game.registerCountChangeListener
import ircover.idlenation.utils.Disposable
import ircover.idlenation.utils.getResourceLinePageFragmentFactory

interface MainActivityPage {
    fun getTitle(): CharSequence
    fun createFragment(): Fragment
    fun getPageTitleModel(): PageTitleModel
}

fun ResourceLine.convertToPage() = object : MainActivityPage {
    var fragment: ResourceLinePageFragment? = null
    private var listenerDisposable: Disposable? = null

    override fun getTitle(): CharSequence =
            "${resourceType.getTitle()}: ${resourceCount.toCommonString()}"

    override fun createFragment(): ResourceLinePageFragment{
        return getResourceLinePageFragmentFactory().createFragmentByType(resourceType).apply {
            fragment = this
        }
    }

    override fun getPageTitleModel(): PageTitleModel =
            PageTitleModel(resourceCount, resourceType).apply {
                listenerDisposable = registerCountChangeListener { count = it }
            }
}