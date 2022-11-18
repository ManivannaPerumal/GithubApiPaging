package assessment.narayanagroup.githubapisearch.base

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

open class BaseViewModel : ViewModel(), LifecycleObserver {
    // All interfaces common exception LiveData data class, mExceptions data in the base class to listen, rewrite onError
    val mExceptions: MutableLiveData<Throwable> = MutableLiveData()

    fun launch(runBlock: suspend CoroutineScope.() -> Unit) {
        launchOnUI {
            tryCatch(runBlock, {}, {}, true)
        }
    }

    //viewModelScope.launch does not declare Dispatchers by default in the main thread
    private fun launchOnUI(runBlock: suspend CoroutineScope.() -> Unit) {
        viewModelScope.launch {
            runBlock()
        }
    }

    private suspend fun tryCatch(
        runBlock: suspend CoroutineScope.() -> Unit,
        catchBlock: suspend CoroutineScope.(Throwable) -> Unit,
        finallyBlock: suspend CoroutineScope.() -> Unit,
        handleCancellationExceptionManually: Boolean = false
    ) {

        // The role of coroutineScope is to automatically switch to the initial thread of the coroutine when the time-consuming task is completed.
        // The coroutine initial thread is the thread of viewModelScope.launch above
        coroutineScope {
            try {
                runBlock()
            } catch (e: Throwable) {
                if (e !is CancellationException || handleCancellationExceptionManually) {
                    mExceptions.value = e
                    catchBlock(e)
                } else {
                    throw e
                }
            } finally {
                finallyBlock()
            }
        }
    }
}