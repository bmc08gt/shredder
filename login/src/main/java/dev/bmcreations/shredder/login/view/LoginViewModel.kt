package dev.bmcreations.shredder.login.view

import dev.bmcreations.shredder.core.architecture.BaseViewModel
import dev.bmcreations.shredder.login.model.LoginViewEffect
import dev.bmcreations.shredder.login.model.LoginViewEvent
import dev.bmcreations.shredder.login.model.LoginViewState

class LoginViewModel private constructor(): BaseViewModel<LoginViewState, LoginViewEvent, LoginViewEffect>(LoginViewState()) {

    companion object {
        fun create(): LoginViewModel {
            return LoginViewModel()
        }
    }
}
