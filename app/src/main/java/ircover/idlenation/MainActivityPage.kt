package ircover.idlenation

import android.support.v4.app.Fragment
import ircover.idlenation.adapters.PageTitleModel
import ircover.idlenation.fragments.ResourceLinePageFragment

interface MainActivityPage {
    fun getTitle(): CharSequence
    fun createFragment(): Fragment
    fun getPageTitleModel(): PageTitleModel
}

fun ResourceLine.convertToPage() = object : MainActivityPage {
    var fragment: ResourceLinePageFragment? = null
    override fun getTitle(): CharSequence =
            "${resourceType.getTitle()}: ${resourceCount.toCommonString()}"

    override fun createFragment(): ResourceLinePageFragment{
        return ResourceLinePageFragment.createByType(resourceType).apply {
            fragment = this
        }
    }

    override fun getPageTitleModel(): PageTitleModel =
            PageTitleModel(resourceCount, resourceType).apply {
                registerOnCountUpdate { count = it }
            }
}