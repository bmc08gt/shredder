package dev.bmcreations.shredder.login.view

import androidx.lifecycle.viewModelScope
import dev.bmcreations.shredder.core.architecture.BaseViewModel
import dev.bmcreations.shredder.core.architecture.ViewStateError
import dev.bmcreations.shredder.core.architecture.ViewStateLoading
import dev.bmcreations.shredder.login.model.*
import dev.bmcreations.shredder.login.usecases.CreateAccountUsecase
import dev.bmcreations.shredder.login.usecases.LoginUsecase
import dev.bmcreations.shredder.login.usecases.ValidateLoginCredentialsUsecase
import dev.bmcreations.shredder.network.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel private constructor(
    private val createAccountUseCase: CreateAccountUsecase,
    private val loginUseCase: LoginUsecase,
    private val validateCredentialsUseCase: ValidateLoginCredentialsUsecase
): BaseViewModel<LoginViewState, LoginViewEvent, LoginViewEffect>(LoginViewState()) {

    override fun process(event: LoginViewEvent) {
        when (event) {
            Swiped -> onSwiped()
            LoginClicked -> onLoginClicked()
            SignUpClicked -> onSignUpClicked()
            is FirstNameUpdated -> onFirstNameUpdated(event.entry)
            is LastNameUpdated -> onLastNameUpdated(event.entry)
            is EmailUpdated -> onEmailUpdated(event.entry)
            is PasswordUpdated -> onPasswordUpdated(event.entry)
            SubmitClicked -> onSubmit()
        }
    }

    private fun onSwiped() {
        effectsEmitter.emit(TriggerSwipeAlphaAnimations)
    }

    private fun onLoginClicked() {
        effectsEmitter.emit(AnimateToLogin)
        setState { copy(isSignUpAttempt = false, error = ViewStateError()) }
    }

    private fun onSignUpClicked() {
        effectsEmitter.emit(AnimateToSignUp)
        setState { copy(isSignUpAttempt = true, error = ViewStateError()) }
    }

    private fun onFirstNameUpdated(firstName: String) {
        setState {
            copy(loginInfo = loginInfo.copy(firstName = firstName), error = ViewStateError())
        }
    }

    private fun onLastNameUpdated(lastName: String) {
        setState {
            copy(loginInfo = loginInfo.copy(lastName = lastName), error = ViewStateError())
        }
    }

    private fun onEmailUpdated(email: String) {
        setState {
            val creds = loginInfo.credentials
            copy(loginInfo = loginInfo.copy(credentials = creds.copy(email = email)), error = ViewStateError())
        }
    }

    private fun onPasswordUpdated(password: String) {
        setState {
            val creds = loginInfo.credentials
            copy(loginInfo = loginInfo.copy(credentials = creds.copy(password = password)), error = ViewStateError())
        }
    }

    private fun onSubmit() {
        informOfLoading("Loading...")
        effectsEmitter.emit(DisableInteraction)
        getLastState()?.let { state ->
            if (state.isSignUpAttempt) {
                val request = state.loginInfo
                validateCredentialsUseCase.execute(request.credentials) {
                    if (it) {
                        createAccountUseCase.execute(request) { result ->
                            when (result) {
                                is NetworkResult.Success -> {
                                    effectsEmitter.emit(DisplaySuccess)
                                    setState {
                                        copy(loading = ViewStateLoading(),
                                            error = ViewStateError(),
                                            loginInfo = this.loginInfo.copy(success = true)
                                        )
                                    }
                                }
                                is NetworkResult.Failure -> {
                                    informOfError(message = result.errorResponse)
                                    effectsEmitter.emit(DisplayErrorAuthenticating)
                                    effectsEmitter.emit(EnableInteraction)
                                }
                            }
                        }
                    } else {
                        informOfError(message = "Password must be equal to or longer than 8 characters")
                        effectsEmitter.emit(DisplayErrorAuthenticating)
                        effectsEmitter.emit(EnableInteraction)
                    }
                }
            } else {
                val request = LoginAttempt(state.loginInfo.credentials)
                loginUseCase.execute(request) { result ->
                    when (result) {
                        is NetworkResult.Success -> {
                            effectsEmitter.emit(DisplaySuccess)
                            setState {
                                copy(loading = ViewStateLoading(),
                                    error = ViewStateError(),
                                    loginInfo = this.loginInfo.copy(success = true)
                                )
                            }
                        }
                        is NetworkResult.Failure -> {
                            informOfError(message = "error")
                            effectsEmitter.emit(DisplayErrorAuthenticating)
                            effectsEmitter.emit(EnableInteraction)
                        }
                    }
                }
            }
        }
    }

    override fun informOfLoading(message: String) {
        viewModelScope.launch(Dispatchers.Main) {
            setState {
                copy(
                    loading = ViewStateLoading(loading = true, message = message),
                    error = ViewStateError()
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
