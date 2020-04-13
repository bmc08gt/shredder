package dev.bmcreations.shredder.core.architecture

import android.util.Log
import androidx.annotation.StringRes
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zhuinden.eventemitter.EventEmitter
import com.zhuinden.eventemitter.EventSource

abstract class BaseViewModel<T: ViewState, R: ViewStateEvent, A: ViewStateEffect> : ViewModel(), ViewModelContract<A> {

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

    protected val eventEmitter = EventEmitter<R>()
    val events: EventSource<R> get() = eventEmitter

    protected val effectsEmitter = EventEmitter<A>()
    val effects: EventSource<A> get() = effectsEmitter

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
