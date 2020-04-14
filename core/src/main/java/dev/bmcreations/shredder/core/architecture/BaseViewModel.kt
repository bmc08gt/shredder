package dev.bmcreations.shredder.core.architecture

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zhuinden.eventemitter.EventEmitter
import com.zhuinden.eventemitter.EventSource

abstract class BaseViewModel<S: ViewState, E: ViewStateEvent, X: ViewStateEffect>(private val initialState: S) : ViewModel() {

    val state: MutableLiveData<S> = MutableLiveData()

    protected fun getLastState(): S? = state.value ?: initialState

    protected val eventEmitter = EventEmitter<E>()
    val events: EventSource<E> get() = eventEmitter

    protected val effectsEmitter = EventEmitter<X>()
    val effects: EventSource<X> get() = effectsEmitter

    init {
        setState { initialState }
    }

    fun setState(stateModifier: S.() -> S) {
        stateModifier.invoke(state.value ?: initialState)
    }

    protected fun generateError(
        exception: Throwable? = null,
        title: String?,
        message: String?
    ): Error = Error(exception = exception, titleString = title, messageString = message)

    protected fun generateError(
        exception: Throwable?,
        titleResId: Int?,
        messageResId: Int?
    ): Error = Error(exception = exception, titleResId = titleResId, messageResId = messageResId)
}
