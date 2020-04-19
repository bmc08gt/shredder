package dev.bmcreations.shredder.core.architecture

interface ViewState {
    val loading: ViewStateLoading
    val error: ViewStateError
}
open class ViewStateEvent
open class ViewStateEffect
