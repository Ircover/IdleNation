package ircover.idlenation.library

import android.databinding.BindingAdapter
import android.support.annotation.ColorRes
import android.support.design.widget.TabLayout
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.widget.RecyclerView
import android.widget.ImageView
import ircover.idlenation.itemDecorators.DividerItemDecorator

@BindingAdapter("android:adapter")
fun setAdapter(viewPager: ViewPager, pagerAdapter: PagerAdapter) {
    viewPager.adapter = pagerAdapter
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
fun bindViewPagerTabs(view: TabLayout, pagerView: ViewPager) {
    view.setupWithViewPager(pagerView, true)
}