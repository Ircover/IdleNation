package ircover.idlenation

import ircover.idlenation.activities.viewModels.MainActivityModel
import ircover.idlenation.activities.viewModels.MainViewModel
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import ircover.idlenation.library.InstantTaskExecutorExtension
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(InstantTaskExecutorExtension::class)
class MainViewModelTest {

    @Test
    @DisplayName("Отбрасывание ненужных вычислений")
    fun threeTimesCalledHeavyProduce_CalculatesTwice() {
        val model = mock(MainActivityModel::class.java)
        val viewModel = MainViewModel(model)
        `when`(model.calculateProduce()).then { Thread.sleep(200) }

        for (i in 0..2) {
            viewModel.calculateProduce()
        }

        verify(model, times(1)).calculateProduce()
    }
}