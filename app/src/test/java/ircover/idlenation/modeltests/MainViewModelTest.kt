package ircover.idlenation.modeltests

import ircover.idlenation.activities.viewModels.MainActivityModel
import ircover.idlenation.activities.viewModels.MainViewModel
import ircover.idlenation.utils.InstantTaskExecutorExtension
import ircover.idlenation.utils.SystemServiceImpl
import ircover.idlenation.utils.mock
import kotlinx.coroutines.test.TestCoroutineContext
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.anyLong
import org.mockito.Mockito.*

@ExtendWith(InstantTaskExecutorExtension::class)
class MainViewModelTest {
    val testContext = TestCoroutineContext()

    @Test
    @DisplayName("Отбрасывание ненужных вычислений")
    fun threeTimesCalledHeavyProduce_CalculatesOnce() {
        val model = mock(MainActivityModel::class.java)
        val viewModel = MainViewModel(model, arrayOf(), mock(), SystemServiceImpl(), testContext)
        `when`(model.calculateProduce(anyLong())).then { Thread.sleep(100) }

        for (i in 0..2) {
            viewModel.calculateProduceAsync()
        }
        testContext.triggerActions()

        verify(model, only()).calculateProduce(anyLong())
    }
}