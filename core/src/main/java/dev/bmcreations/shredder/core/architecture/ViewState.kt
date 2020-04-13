package dev.bmcreations.shredder.core.architecture

interface ViewState {
    val loading: Loading
    val error: Error
}
open class ViewStateEvent
open class ViewStateEffect
