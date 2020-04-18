package dev.bmcreations.shredder.login.repository

import dev.bmcreations.shredder.login.model.LoginCredential
import dev.bmcreations.shredder.models.UserLogin

interface LoginRepository {
    fun validateCredentials(credentials: LoginCredential, cb: (Boolean) -> Unit)
    fun createAccount(credentials: LoginCredential, cb: (UserLogin) -> Unit)
    fun login(credentials: LoginCredential, cb: (UserLogin) -> Unit)
}
