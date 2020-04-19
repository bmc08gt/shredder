package dev.bmcreations.shredder.login.view

import androidx.lifecycle.viewModelScope
import dev.bmcreations.shredder.core.architecture.BaseViewModel
import dev.bmcreations.shredder.core.architecture.ViewStateLoading
import dev.bmcreations.shredder.login.model.LoginViewEffect
import dev.bmcreations.shredder.login.model.LoginViewEvent
import dev.bmcreations.shredder.login.model.LoginViewState
import dev.bmcreations.shredder.login.usecases.CreateAccountUsecase
import dev.bmcreations.shredder.login.usecases.LoginUsecase
import dev.bmcreations.shredder.login.usecases.ValidateLoginCredentialsUsecase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel private constructor(
    private val createAccountUseCase: CreateAccountUsecase,
    private val loginUseCase: LoginUsecase,
    private val validateCredentialsUseCase: ValidateLoginCredentialsUsecase
): BaseViewModel<LoginViewState, LoginViewEvent, LoginViewEffect>(LoginViewState()) {

    override fun informOfLoading(message: String) {
        viewModelScope.launch(Dispatchers.Main) {
            setState {
                copy(
                    loading = ViewStateLoading(loading = true, message = message)
                )
            }
        }
    }

    override fun informOfError(exception: Throwable?, title: String?, message: String?) {
        viewModelScope.launch(Dispatchers.Main) {
            setState {
                copy(
                    loading = ViewStateLoading(),
                    error = generateError(exception, title, message)
                )
            }
        }
    }

    override fun informOfError(exception: Throwable?, titleResId: Int?, messageResId: Int?) {
        viewModelScope.launch(Dispatchers.Main) {
            setState {
                copy(
                    loading = ViewStateLoading(),
                    error = generateError(exception, titleResId, messageResId)
                )
            }
        }
    }


    companion object {
        fun create(
            createAccountUseCase: CreateAccountUsecase,
            loginUseCase: LoginUsecase,
            validateCredentialsUseCase: ValidateLoginCredentialsUsecase
        ): LoginViewModel {
            return LoginViewModel(createAccountUseCase, loginUseCase, validateCredentialsUseCase)
        }
    }
}
