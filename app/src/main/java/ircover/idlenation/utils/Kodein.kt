package ircover.idlenation.utils

import android.arch.lifecycle.ViewModel
import android.os.SystemClock
import com.github.salomonbrys.kodein.*
import com.github.salomonbrys.kodein.conf.ConfigurableKodein
import ircover.idlenation.activities.viewModels.MainActivityModel
import ircover.idlenation.activities.viewModels.MainViewModel
import ircover.idlenation.fragments.ResourceLinePageFragmentFactory
import ircover.idlenation.fragments.ResourceLinePageFragmentFactoryImpl
import ircover.idlenation.game.BonusWorker
import ircover.idlenation.game.BonusWorkerImpl
import ircover.idlenation.game.ResourceLinesProvider
import ircover.idlenation.game.ResourceLinesProviderImpl
import kotlinx.coroutines.*

const val ELAPSED_REALTIME_TAG = "elapsed_realtime"
class KodeinWorker {
    companion object {
        val kodein = ConfigurableKodein(mutable = true).apply {
            addConfig {
                bind<CoroutineDispatcher>() with singleton { Dispatchers.Main }
                bind<Long>(ELAPSED_REALTIME_TAG) with provider { SystemClock.elapsedRealtime() }
                bind<PreferencesManager>() with singleton { PreferencesManagerImpl() }
                bind<BonusWorker>() with singleton { BonusWorkerImpl() }
                bind<ResourceLinesProvider>() with singleton { ResourceLinesProviderImpl(instance()) }
                bind<ResourceLinePageFragmentFactory>() with singleton { ResourceLinePageFragmentFactoryImpl() }
                bind<SystemService>() with singleton { SystemServiceImpl() }
                bind<PagesFactory>() with singleton { PagesFactoryImpl(instance(), instance()) }
                bind<Printer>() with singleton { PrinterImpl(instance()) }
                bindViewModels()
            }
        }

        private fun Kodein.Builder.bindViewModels() {
            bindViewModel<MainViewModel>() with provider {
                MainViewModel(MainActivityModel(),
                        instance<ResourceLinesProvider>().resourceLines,
                        instance(),
                        instance(),
                        Dispatchers.Main)
            }
        }

        private inline fun <reified T : ViewModel> Kodein.Builder.bindViewModel() =
                this.bind<ViewModel>(T::class.java)
    }
}

private inline fun <reified T: Any> kodeinResolve(tag: Any? = null) =
        KodeinWorker.kodein.instance<T>(tag)

fun runOnUI(action: suspend CoroutineScope.() -> Unit) = GlobalScope.launch(
        kodeinResolve<CoroutineDispatcher>(), CoroutineStart.DEFAULT, action)