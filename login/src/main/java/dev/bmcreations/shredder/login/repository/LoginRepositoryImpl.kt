package dev.bmcreations.shredder.login.repository

import androidx.core.text.trimmedLength
import dev.bmcreations.shredder.core.preferences.UserPreferences
import dev.bmcreations.shredder.login.model.LoginAttempt
import dev.bmcreations.shredder.login.model.LoginCredential
import dev.bmcreations.shredder.login.model.SignUpAttempt
import dev.bmcreations.shredder.models.CreateUserRequest
import dev.bmcreations.shredder.models.User
import dev.bmcreations.shredder.network.NetworkResult
import dev.bmcreations.shredder.network.RequestResolver
import dev.bmcreations.shredder.network.login.repository.LoginRepository
import dev.bmcreations.shredder.network.user.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginRepositoryImpl(
    private val prefs: UserPreferences,
    private val loginWebService: LoginRepository,
    private val userWebService: UserRepository
) : LoginViewRepository, RequestResolver, CoroutineScope by CoroutineScope(Dispatchers.Main) {
    override fun validateCredentials(credentials: LoginCredential, cb: (Boolean) -> Unit) {
        cb.invoke(credentials.password.trimmedLength() >= 8)
    }

    override fun createAccount(attempt: SignUpAttempt, cb: (NetworkResult<User>) -> Unit) {
        launch {

            val request = CreateUserRequest(attempt.firstName, attempt.lastName, attempt.credentials.email, attempt.credentials.password)
            val result = userWebService.createUser(request)
            if (result is NetworkResult.Success) {
                saveUser(result.body)
            }
            cb.invoke(result)
        }
    }

    override fun login(attempt: LoginAttempt, cb: (NetworkResult<User>) -> Unit) {
        launch {
            val result = loginWebService.login(attempt.credentials.email, attempt.credentials.password)
            if (result is NetworkResult.Success) {
                saveUser(result.body)
            }
            cb.invoke(result)
        }
    }

    private fun saveUser(user: User) {
        prefs.user = user
    }
}
