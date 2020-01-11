package ircover.idlenation.adapters

import android.databinding.ObservableField
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentStatePagerAdapter
import ircover.idlenation.MainActivityPage
import ircover.idlenation.game.ResourceType
import ircover.idlenation.utils.BaseActivity
import ircover.idlenation.utils.Printer
import org.apfloat.Apfloat

class PageTitleModel(startCount: Apfloat,
                     resourceType: ResourceType,
                     private val printer: Printer) {
    var count: Apfloat = startCount
    set(value) {
        field = value
        countString.notifyChange()
    }
    val countString = object : ObservableField<String>() {
        override fun get() = printer.printApfloat(count)
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