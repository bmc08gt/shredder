package dev.bmcreations.shredder.core.architecture

import androidx.annotation.StringRes
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zhuinden.eventemitter.EventEmitter
import com.zhuinden.eventemitter.EventSource

abstract class BaseViewModel<S: ViewState, E: ViewStateEvent, X: ViewStateEffect>(private val initialState: S) : ViewModel(), ViewModelContract<E> {

    val state: MutableLiveData<S> = MutableLiveData()

    protected fun getLastState(): S? = state.value ?: initialState

    protected val effectsEmitter = EventEmitter<X>()
    val effects: EventSource<X> get() = effectsEmitter

    init {
        setState { initialState }
    }

    fun setState(stateModifier: S.() -> S) {
        state.value = stateModifier.invoke(state.value ?: initialState)
    }

    abstract fun informOfLoading(message: String)

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

    protected fun generateError(
        exception: Throwable? = null,
        title: String?,
        message: String?
    ): ViewStateError = ViewStateError(exception = exception, titleString = title, messageString = message)

    protected fun generateError(
        exception: Throwable?,
        titleResId: Int?,
        messageResId: Int?
    ): ViewStateError = ViewStateError(exception = exception, titleResId = titleResId, messageResId = messageResId)
}

/**
 * Internal Contract to be implemented by ViewModel
 * Required to intercept and log ViewEvents
 */
internal interface ViewModelContract<E: ViewStateEvent> {
    fun process(event: E)
}
