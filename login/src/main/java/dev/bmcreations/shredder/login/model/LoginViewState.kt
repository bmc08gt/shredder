package dev.bmcreations.shredder.login.model

import dev.bmcreations.shredder.core.architecture.*


data class LoginViewState(
    override val loading: ViewStateLoading = ViewStateLoading(),
    override val error: ViewStateError = ViewStateError(),
    val loginInfo: SignUpAttempt = SignUpAttempt(firstName = "", lastName = "", credentials = LoginCredential("", "")), // sign up attempt contains ALL info
    val isSignUpAttempt: Boolean = false
) : ViewState

sealed class LoginViewEvent : ViewStateEvent()
object Swiped: LoginViewEvent()
object LoginClicked: LoginViewEvent()
object SignUpClicked: LoginViewEvent()
data class FirstNameUpdated(val entry: String): LoginViewEvent()
data class LastNameUpdated(val entry: String): LoginViewEvent()
data class EmailUpdated(val entry: String): LoginViewEvent()
data class PasswordUpdated(val entry: String): LoginViewEvent()
object SubmitClicked: LoginViewEvent()

sealed class LoginViewEffect : ViewStateEffect()
object TriggerSwipeAlphaAnimations: LoginViewEffect()
object AnimateToSignUp: LoginViewEffect()
object AnimateToLogin : LoginViewEffect()
object DisplayErrorAuthenticating: LoginViewEffect()
object DisplaySuccess : LoginViewEffect()
object DisableInteraction : LoginViewEffect()
object EnableInteraction : LoginViewEffect()
