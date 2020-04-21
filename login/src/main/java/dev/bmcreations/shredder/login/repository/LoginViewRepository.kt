package dev.bmcreations.shredder.login.repository

import dev.bmcreations.shredder.login.model.LoginAttempt
import dev.bmcreations.shredder.login.model.LoginCredential
import dev.bmcreations.shredder.login.model.SignUpAttempt
import dev.bmcreations.shredder.models.UserLogin
import dev.bmcreations.shredder.network.NetworkResult

interface LoginViewRepository {
    fun validateCredentials(credentials: LoginCredential, cb: (Boolean) -> Unit)
    fun createAccount(credentials: SignUpAttempt, cb: (NetworkResult<UserLogin>) -> Unit)
    fun login(credentials: LoginAttempt, cb: (NetworkResult<UserLogin>) -> Unit)
}
