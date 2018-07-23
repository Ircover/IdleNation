package ircover.idlenation.fragments

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ircover.idlenation.R
import ircover.idlenation.ResourceType
import ircover.idlenation.activities.viewModels.MainViewModel
import ircover.idlenation.databinding.FragmentResourceLinePageBinding

abstract class ResourceLinePageFragment : Fragment() {
    private var binding: FragmentResourceLinePageBinding? = null
    private val model = ResourceLineModel { activity }
    abstract val resourceType: ResourceType

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as? AppCompatActivity)?.let { activity ->
            ViewModelProviders.of(activity).get(MainViewModel::class.java)
                    .observeResourceLines(activity) {
                        model.resourceLine = it.find { it.resourceType == resourceType }
                        linkBindingWithPage()
                    }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_resource_line_page, container, false)
        linkBindingWithPage()
        return binding?.root
    }

    private fun linkBindingWithPage() {
        binding?.resourceLineModel = model
    }

    companion object {
        fun createByType(resourceType: ResourceType): ResourceLinePageFragment =
                when(resourceType) {
                    ResourceType.Food -> FoodLinePageFragment()
                }
    }
}

class FoodLinePageFragment : ResourceLinePageFragment() {
    override val resourceType: ResourceType
        get() = ResourceType.Food
}