package dev.bmcreations.shredder.login.usecases

import dev.bmcreations.shredder.core.architecture.ParameterUsecaseWithCallback
import dev.bmcreations.shredder.login.model.LoginCredential
import dev.bmcreations.shredder.login.repository.LoginRepository
import dev.bmcreations.shredder.models.UserLogin

class LoginUsecase(
    private val repository: LoginRepository
): ParameterUsecaseWithCallback<LoginCredential, UserLogin>() {
    override fun execute(p0: LoginCredential, cb: (UserLogin) -> Unit) {
        repository.login(p0, cb)
    }
}
