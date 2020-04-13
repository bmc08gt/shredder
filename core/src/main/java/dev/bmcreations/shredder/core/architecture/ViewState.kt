package dev.bmcreations.shredder.core.architecture

open class ViewState(
    open val loading: Loading = Loading(),
    open val error: Error = Error()
)
open class ViewStateEvent
open class ViewStateEffect
