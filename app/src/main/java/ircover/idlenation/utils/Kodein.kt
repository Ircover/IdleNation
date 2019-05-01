package ircover.idlenation.utils

import android.os.SystemClock
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.conf.ConfigurableKodein
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.provider
import com.github.salomonbrys.kodein.singleton
import kotlinx.coroutines.*

const val ELAPSED_REALTIME_TAG = "elapsed_realtime"
class KodeinWorker {
    companion object {
        val kodein = ConfigurableKodein(mutable = true).apply {
            addConfig {
                bind<CoroutineDispatcher>() with singleton { Dispatchers.Main }
                bind<Long>(ELAPSED_REALTIME_TAG) with provider { SystemClock.elapsedRealtime() }
            }
        }
    }
}

private inline fun <reified T: Any> kodeinResolve(tag: Any? = null) =
        KodeinWorker.kodein.instance<T>(tag)

fun getElapsedRealtime(): Long = kodeinResolve(ELAPSED_REALTIME_TAG)
fun runOnUI(action: suspend CoroutineScope.() -> Unit) = GlobalScope.launch(
        kodeinResolve<CoroutineDispatcher>(), CoroutineStart.DEFAULT, action)