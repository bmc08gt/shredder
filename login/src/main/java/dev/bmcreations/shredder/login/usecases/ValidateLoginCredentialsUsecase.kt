package dev.bmcreations.shredder.login.usecases

import dev.bmcreations.shredder.core.architecture.ParameterUsecaseWithCallback
import dev.bmcreations.shredder.login.model.LoginCredential
import dev.bmcreations.shredder.login.repository.LoginViewRepository

class ValidateLoginCredentialsUsecase(
    private val repository: LoginViewRepository
): ParameterUsecaseWithCallback<LoginCredential, Boolean>() {
    override fun execute(p0: LoginCredential, cb: (Boolean) -> Unit) {
        repository.validateCredentials(p0, cb)
    }
}
