package dev.bmcreations.shredder.login.model

import dev.bmcreations.shredder.core.architecture.*


data class LoginViewState(
    override val loading: ViewStateLoading = ViewStateLoading(),
    override val error: ViewStateError = ViewStateError()
) : ViewState

sealed class LoginViewEvent : ViewStateEvent() {
    object LoginSelected: ViewStateEvent()
    object CreateAccountSelected: ViewStateEvent()
    object AccountCreatedSuccessful: ViewStateEvent()
    object LoginSuccessful : ViewStateEvent()
}

sealed class LoginViewEffect : ViewStateEffect()
