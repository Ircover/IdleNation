package ircover.idlenation.utils

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

abstract class BaseActivity<TBinding : ViewDataBinding, TModel : BaseViewModel<*>>(contentResId: Int) :
        AppCompatActivity() {
    abstract val viewModelClass: Class<TModel>
    protected val binding: TBinding by ActivityBinding(contentResId)
    protected val viewModel: TModel by lazy { getViewModelFromProvider() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinding(binding)
    }

    private fun getViewModelFromProvider(): TModel =
            ViewModelProviders.of(this).get(viewModelClass)

    abstract fun initBinding(binding: TBinding)

    protected fun <T> LiveData<T>.observe(observer: (T) -> Unit) {
        observe(this@BaseActivity, Observer { it?.let(observer) })
    }
}