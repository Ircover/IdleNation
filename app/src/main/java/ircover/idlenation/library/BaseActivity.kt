package ircover.idlenation.library

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.reactivex.disposables.Disposable

abstract class BaseActivity<TBinding : ViewDataBinding, TModel : BaseViewModel<*>> :
        AppCompatActivity() {
    abstract val contentResId: Int
    abstract val viewModelClass: Class<TModel>
    protected val binding: TBinding by ActivityBinding(contentResId)
    protected val viewModel: TModel by lazy { getViewModelFromProvider() }
    private val uiSubscriptions = arrayListOf<Disposable>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinding(binding)
    }

    protected fun addUiSubscription(subscription: Disposable) {
        uiSubscriptions.add(subscription)
    }

    override fun onPause() {
        super.onPause()
        uiSubscriptions.forEach { it.dispose() }
        uiSubscriptions.clear()
    }

    private fun getViewModelFromProvider(): TModel = ViewModelProviders.of(this).get(viewModelClass)

    abstract fun initBinding(binding: TBinding)

    protected fun <T> LiveData<T>.observe(observer: (T) -> Unit) {
        observe(this@BaseActivity, Observer { it?.let(observer) })
    }
}