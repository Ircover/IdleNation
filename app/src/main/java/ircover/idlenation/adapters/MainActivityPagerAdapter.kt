package ircover.idlenation.adapters

import android.databinding.ObservableField
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentStatePagerAdapter
import ircover.idlenation.*
import ircover.idlenation.library.BaseActivity
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
    var pages: List<MainActivityPage> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    override fun getItem(position: Int): Fragment =
            pages[position].createFragment()

    override fun getCount(): Int = pages.size
}