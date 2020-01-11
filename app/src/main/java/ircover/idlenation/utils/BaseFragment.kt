package ircover.idlenation.utils

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.salomonbrys.kodein.KodeinAware

abstract class BaseFragment<T : ViewDataBinding> : Fragment(), KodeinAware {
    override val kodein = KodeinWorker.kodein
    protected var binding: T? = null
    abstract val layoutResId: Int

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater,
                layoutResId, container, false)
        linkBindingWithModel()
        return binding?.root
    }

    protected fun <T> LiveData<T>.observeIfAppCompat(observer: (T) -> Unit) {
        (activity as? AppCompatActivity)?.let { activity ->
            observe(activity, Observer { it?.let(observer) })
        }
    }

    abstract fun linkBindingWithModel()
}