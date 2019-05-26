package ircover.idlenation.utils

import android.os.SystemClock
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.conf.ConfigurableKodein
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.provider
import com.github.salomonbrys.kodein.singleton
import ircover.idlenation.PreferencesManager
import ircover.idlenation.PreferencesManagerImpl
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
                bind<ResourceLinesProvider>() with singleton { ResourceLinesProviderImpl() }
                bind<ResourceLinePageFragmentFactory>() with singleton { ResourceLinePageFragmentFactoryImpl() }
            }
        }
    }
}

private inline fun <reified T: Any> kodeinResolve(tag: Any? = null) =
        KodeinWorker.kodein.instance<T>(tag)

fun getElapsedRealtime(): Long = kodeinResolve(ELAPSED_REALTIME_TAG)
fun getPreferencesManager(): PreferencesManager = kodeinResolve()
fun getBonusWorker(): BonusWorker = kodeinResolve()
fun getResourceLinesProvider(): ResourceLinesProvider = kodeinResolve()
fun getResourceLinePageFragmentFactory(): ResourceLinePageFragmentFactory = kodeinResolve()
fun runOnUI(action: suspend CoroutineScope.() -> Unit) = GlobalScope.launch(
        kodeinResolve<CoroutineDispatcher>(), CoroutineStart.DEFAULT, action)