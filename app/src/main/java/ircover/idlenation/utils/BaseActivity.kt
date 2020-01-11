package ircover.idlenation.utils

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.github.salomonbrys.kodein.KodeinAware
import com.github.salomonbrys.kodein.instance

abstract class BaseActivity<TBinding : ViewDataBinding, TModel : BaseViewModel<*>>(contentResId: Int) :
        AppCompatActivity(), KodeinAware {
    abstract val viewModelClass: Class<TModel>
    override val kodein = KodeinWorker.kodein
    protected val binding: TBinding by ActivityBinding(contentResId)
    protected val viewModel: TModel by lazy { getViewModelFromProvider() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinding(binding)
    }

    private fun getViewModelFromProvider(): TModel =
            ViewModelProviders.of(this, ViewModelFactory(kodein)).get(viewModelClass).apply {
                systemService = kodein.instance()
            }

    abstract fun initBinding(binding: TBinding)

    protected fun <T> LiveData<T>.observe(observer: (T) -> Unit) {
        observe(this@BaseActivity, Observer { it?.let(observer) })
    }
}