package dev.bmcreations.shredder.login.usecases

import dev.bmcreations.shredder.core.architecture.ParameterUsecaseWithCallback
import dev.bmcreations.shredder.login.model.SignUpAttempt
import dev.bmcreations.shredder.login.repository.LoginViewRepository
import dev.bmcreations.shredder.models.User
import dev.bmcreations.shredder.models.UserLogin
import dev.bmcreations.shredder.network.NetworkResult

class CreateAccountUsecase(
    private val repository: LoginViewRepository
): ParameterUsecaseWithCallback<SignUpAttempt, NetworkResult<User>>() {
    override fun execute(p0: SignUpAttempt, cb: (NetworkResult<User>) -> Unit) {
        repository.createAccount(p0, cb)
    }
}
