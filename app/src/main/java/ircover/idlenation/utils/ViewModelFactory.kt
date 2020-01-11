package ircover.idlenation.utils

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.instance

class ViewModelFactory(private val kodein: Kodein) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
            kodein.instance<ViewModel>(modelClass) as? T
                    ?: modelClass.newInstance()
}