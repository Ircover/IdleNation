package ircover.idlenation.library

import android.databinding.BindingAdapter
import android.support.annotation.ColorRes
import android.support.design.widget.TabLayout
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import ircover.idlenation.adapters.ResourceLineAdapter
import ircover.idlenation.itemdecorators.DividerItemDecorator

interface TabLayoutTitleProcessor {
    fun process(parent: TabLayout, tab: TabLayout.Tab, position: Int)
}

@BindingAdapter("android:adapter")
fun setAdapter(viewPager: ViewPager, pagerAdapter: PagerAdapter?) {
    if(pagerAdapter != null) {
        viewPager.adapter = pagerAdapter
    }
}

@BindingAdapter("android:adapter")
fun setAdapter(recyclerView: RecyclerView, adapter: RecyclerView.Adapter<*>) {
    recyclerView.adapter = adapter
}

@BindingAdapter("android:dividerDecoratorColor")
fun setDividerDecorator(recyclerView: RecyclerView, @ColorRes dividerColor: Int) {
    recyclerView.addItemDecoration(DividerItemDecorator(recyclerView.context, dividerColor))
}

@BindingAdapter("android:source")
fun setImageResource(imageView: ImageView, resource: Int) {
    imageView.setImageResource(resource)
}

@BindingAdapter("android:bindWithPager")
fun bindViewPagerTabs(view: TabLayout, pagerView: ViewPager?) {
    view.setupWithViewPager(pagerView, true)
}

@BindingAdapter("android:showOnSelectInAdapter")
fun setViewToShowOnSelect(view: View, adapter: ResourceLineAdapter?) {
    adapter?.viewToShowDetails = view
}

@BindingAdapter("android:viewPager", "android:customTitles", "android:adapter")
fun setCustomTitles(tabLayout: TabLayout,
                    viewPager: ViewPager?,
                    titlesProcessor: TabLayoutTitleProcessor?,
                    adapter: PagerAdapter?) {
    if(viewPager != null) {
        setAdapter(viewPager, adapter)
    }
    bindViewPagerTabs(tabLayout, viewPager)
    if(titlesProcessor != null) {
        for (i in 0 until tabLayout.tabCount) {
            tabLayout.getTabAt(i)?.let { tab ->
                titlesProcessor.process(tabLayout, tab, i)
            }
        }
    }
}