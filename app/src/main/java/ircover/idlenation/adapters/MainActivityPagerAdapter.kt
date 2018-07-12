package ircover.idlenation.adapters

import android.databinding.DataBindingUtil
import android.databinding.ObservableField
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentStatePagerAdapter
import android.view.View
import android.view.ViewGroup
import ircover.idlenation.*
import ircover.idlenation.databinding.ViewPageTitleBinding
import ircover.idlenation.library.BaseActivity
import ircover.idlenation.library.commonFunctions.getLayoutInflater
import org.apfloat.Apfloat

class PageTitleModel(startCount: Apfloat, resourceType: ResourceType) {
    var count: Apfloat = startCount
    set(value) {
        field = value
        countString.notifyChange()
    }
    val countString = object : ObservableField<String>() {
        override fun get() = count.toCommonString()
    }

    val iconResource = resourceType.getIconResourceId()
}

class MainActivityPagerAdapter(activity: BaseActivity<*, *>) :
        FragmentStatePagerAdapter(activity.supportFragmentManager) {
    private var pages: List<MainActivityPage> = listOf()
    var resourceLines: Array<ResourceLine> = arrayOf()
        set(value) {
            field = value
            resetPages()
            notifyDataSetChanged()
        }
    override fun getItem(position: Int): Fragment =
            pages[position].createFragment()

    private fun resetPages() {
        pages = resourceLines.map { it.convertToPage() }
    }

    override fun getCount(): Int = pages.size

    fun processTabs(tabLayout: TabLayout) {
        for(i in 0 until tabLayout.tabCount) {
            tabLayout.getTabAt(i)?.customView = createPageTabView(tabLayout, pages[i].getPageTitleModel())
        }
    }

    private fun createPageTabView(parent: ViewGroup, pageModel: PageTitleModel): View =
            DataBindingUtil.inflate<ViewPageTitleBinding>(parent.context.getLayoutInflater(),
                    R.layout.view_page_title, parent, false).apply {
                model = pageModel
            }.root
}