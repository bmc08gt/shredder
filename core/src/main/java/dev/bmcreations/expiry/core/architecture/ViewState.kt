package dev.bmcreations.expiry.core.architecture

open class ViewState(
    open val loading: Loading = Loading(),
    open val error: Error = Error()
)
open class ViewStateRequest
open class ViewStateAction
