package ircover.idlenation.modeltests

import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.singleton
import ircover.idlenation.activities.viewModels.MainActivityModel
import ircover.idlenation.activities.viewModels.MainViewModel
import ircover.idlenation.utils.ELAPSED_REALTIME_TAG
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import ircover.idlenation.utils.InstantTaskExecutorExtension
import ircover.idlenation.utils.KodeinWorker
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(InstantTaskExecutorExtension::class)
class MainViewModelTest {

    @BeforeEach
    fun setup() {
        KodeinWorker.kodein.addConfig {
            bind<Long>(ELAPSED_REALTIME_TAG, overrides = true) with singleton {
                System.currentTimeMillis()
            }
        }
    }

    @Test
    @DisplayName("Отбрасывание ненужных вычислений")
    fun threeTimesCalledHeavyProduce_CalculatesOnce() = runBlocking {
        val model = mock(MainActivityModel::class.java)
        val viewModel = MainViewModel(model)
        `when`(model.calculateProduce()).then { Thread.sleep(100) }
        val jobsList: ArrayList<Deferred<Unit>> = arrayListOf()

        for (i in 0..2) {
            jobsList.add(viewModel.calculateProduce())
        }
        jobsList.forEach { it.await() }

        verify(model, only()).calculateProduce()
    }
}