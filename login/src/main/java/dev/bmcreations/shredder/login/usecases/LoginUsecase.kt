package dev.bmcreations.shredder.login.usecases

import dev.bmcreations.shredder.core.architecture.ParameterUsecaseWithCallback
import dev.bmcreations.shredder.login.model.LoginAttempt
import dev.bmcreations.shredder.login.repository.LoginViewRepository
import dev.bmcreations.shredder.models.User
import dev.bmcreations.shredder.models.UserLogin
import dev.bmcreations.shredder.network.NetworkResult

class LoginUsecase(
    private val repository: LoginViewRepository
): ParameterUsecaseWithCallback<LoginAttempt, NetworkResult<User>>() {
    override fun execute(p0: LoginAttempt, cb: (NetworkResult<User>) -> Unit) {
        repository.login(p0, cb)
    }
}
