package ircover.idlenation.fragments

import android.os.Bundle
import ircover.idlenation.R
import ircover.idlenation.game.ResourceType
import ircover.idlenation.activities.viewModels.MainViewModel
import ircover.idlenation.databinding.FragmentResourceLinePageBinding
import ircover.idlenation.fragments.model.ResourceLineModel
import ircover.idlenation.utils.BaseFragment
import ircover.idlenation.utils.commonFunctions.getViewModel

abstract class ResourceLinePageFragment : BaseFragment<FragmentResourceLinePageBinding>() {
    override val layoutResId = R.layout.fragment_resource_line_page
    private val model = ResourceLineModel()
    abstract val resourceType: ResourceType

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.getViewModel<MainViewModel>()
                ?.findResourceLine(resourceType)
                ?.observeIfAppCompat { resourceLine ->
                    model.resourceLine = resourceLine
                    linkBindingWithModel()
                }
        model.onWorkPlaceSelect = { binding?.workPlaceModel = it }
    }

    override fun linkBindingWithModel() {
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