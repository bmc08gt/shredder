package dev.bmcreations.shredder.core.architecture

import android.util.Log
import androidx.annotation.StringRes
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zhuinden.eventemitter.EventEmitter
import com.zhuinden.eventemitter.EventSource

abstract class BaseViewModel<T: ViewState, R: ViewStateRequest, A: ViewStateAction> : ViewModel(), ViewModelContract<A> {

    abstract fun informOfError(
        exception: Throwable? = null,
        title: String? = null,
        message: String?
    )

    abstract fun informOfError(
        exception: Throwable? = null,
        @StringRes titleResId: Int? = null,
        @StringRes messageResId: Int?
    )

    val state: MutableLiveData<T> = MutableLiveData()

    fun getLastState(): T? = state.value

    protected val requestEmitter = EventEmitter<R>()
    val requests: EventSource<R> get() = requestEmitter

    protected val actionEmitter = EventEmitter<A>()
    val actions: EventSource<A> get() = actionEmitter

    override fun process(viewEvent: A) {
        Log.d(javaClass.simpleName, "processing event: $viewEvent")
    }
}

/**
 * Internal Contract to be implemented by ViewModel
 * Required to intercept and log ViewEvents
 */
internal interface ViewModelContract<EVENT> {
    fun process(viewEvent: EVENT)
}
