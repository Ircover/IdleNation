package ircover.idlenation.activities

import android.os.Bundle
import ircover.idlenation.R
import ircover.idlenation.activities.viewModels.MainViewModel
import ircover.idlenation.adapters.MainActivityPagerAdapter
import ircover.idlenation.databinding.ActivityMainBinding
import ircover.idlenation.utils.BaseActivity

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(R.layout.activity_main) {
    override val viewModelClass = MainViewModel::class.java

    private lateinit var pagerAdapter: MainActivityPagerAdapter

    override fun initBinding(binding: ActivityMainBinding) {
        pagerAdapter = MainActivityPagerAdapter(this)
        binding.model = viewModel
        binding.pagerAdapter = pagerAdapter
        binding.executePendingBindings()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.menuPages.observe { menuPages ->
            pagerAdapter.pages = menuPages
            viewModel.notifyPagesViewed()
        }
        viewModel.startCalculateProducing(this)
    }
}
