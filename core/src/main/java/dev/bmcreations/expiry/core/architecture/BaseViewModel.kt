package dev.bmcreations.expiry.core.architecture

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel

abstract class BaseViewModel<T> : ViewModel() {

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
}

interface ViewStateResultImpl<in T> where T: ViewStateResult {
    fun informOfResult(result: T)
}

interface ViewStateRequestImpl<in T> where T: ViewStateRequest {
    fun informOfRequest(request: T)
}

open class ViewState(
    open val loading: Boolean = false,
    open val request: ViewStateRequest = ViewStateRequest(),
    open val result: ViewStateResult = ViewStateResult(),
    open val error: Error = Error()
)
open class ViewStateRequest
open class ViewStateResult
