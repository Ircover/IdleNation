package ircover.idlenation

import android.support.v4.app.Fragment
import ircover.idlenation.adapters.PageTitleModel

interface MainActivityPage {
    fun getTitle(): CharSequence
    fun createFragment(): Fragment
    fun getPageTitleModel(): PageTitleModel
}