package dev.bmcreations.shredder.login.repository

import dev.bmcreations.shredder.core.preferences.UserPreferences
import dev.bmcreations.shredder.login.model.LoginAttempt
import dev.bmcreations.shredder.login.model.LoginCredential
import dev.bmcreations.shredder.login.model.SignUpAttempt
import dev.bmcreations.shredder.models.UserLogin
import dev.bmcreations.shredder.network.NetworkResult
import dev.bmcreations.shredder.network.login.repository.LoginRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class LoginRepositoryImpl(
    private val prefs: UserPreferences,
    private val webService: LoginRepository
) : LoginViewRepository, CoroutineScope by CoroutineScope(Dispatchers.Main) {
    override fun validateCredentials(credentials: LoginCredential, cb: (Boolean) -> Unit) {
        cb.invoke(false)
    }

    override fun createAccount(credentials: SignUpAttempt, cb: (NetworkResult<UserLogin>) -> Unit) {
        cb.invoke(NetworkResult.Failure())
    }

    override fun login(credentials: LoginAttempt, cb: (NetworkResult<UserLogin>) -> Unit) {
        cb.invoke(NetworkResult.Failure())
    }
}
