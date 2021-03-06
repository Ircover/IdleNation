package ircover.idlenation.activities

import android.os.Bundle
import io.reactivex.Observable
import ircover.idlenation.R
import ircover.idlenation.activities.viewModels.MainViewModel
import ircover.idlenation.adapters.MainActivityPagerAdapter
import ircover.idlenation.databinding.ActivityMainBinding
import ircover.idlenation.library.BaseActivity
import java.util.concurrent.TimeUnit

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {
    override val viewModelClass = MainViewModel::class.java

    override val contentResId = R.layout.activity_main
    private lateinit var pagerAdapter: MainActivityPagerAdapter

    override fun initBinding(binding: ActivityMainBinding) {
        pagerAdapter = MainActivityPagerAdapter(this)
        binding.model = viewModel
        binding.pagerAdapter = pagerAdapter
        binding.executePendingBindings()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.tlMain.setupWithViewPager(binding.viewPager)
        viewModel.observeResourceLines(this) { resourceLines ->
            pagerAdapter.resourceLines = resourceLines
            pagerAdapter.processTabs(binding.tlMain)
        }
    }

    override fun onResume() {
        super.onResume()
        addUiSubscription(Observable.interval(16L, TimeUnit.MILLISECONDS)
                .subscribe {
                    viewModel.calculateProduce()
                })
    }
}
