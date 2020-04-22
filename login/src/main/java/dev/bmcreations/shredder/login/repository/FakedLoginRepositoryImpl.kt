package dev.bmcreations.shredder.login.repository

import dev.bmcreations.shredder.core.preferences.UserPreferences
import dev.bmcreations.shredder.login.model.LoginAttempt
import dev.bmcreations.shredder.login.model.LoginCredential
import dev.bmcreations.shredder.login.model.SignUpAttempt
import dev.bmcreations.shredder.models.User
import dev.bmcreations.shredder.models.UserLogin
import dev.bmcreations.shredder.network.NetworkResult
import kotlinx.coroutines.*
import java.lang.Thread.sleep
import java.util.*

class FakedLoginRepositoryImpl(
    private val prefs: UserPreferences
) : LoginViewRepository, CoroutineScope by CoroutineScope(Dispatchers.Main) {

    private var failedOnce = false

    override fun validateCredentials(credentials: LoginCredential, cb: (Boolean) -> Unit) {
        launch(Dispatchers.IO) {
            sleep(100)
            withContext(Dispatchers.Main) { cb.invoke(true) }
        }
    }

    override fun createAccount(attempt: SignUpAttempt, cb: (NetworkResult<User>) -> Unit) {
        launch(Dispatchers.IO) {
            sleep(1000)
            val user = createUser(attempt)
            withContext(Dispatchers.Main) {
                cb.invoke(NetworkResult.Success(user))
            }
        }
    }

    override fun login(attempt: LoginAttempt, cb: (NetworkResult<User>) -> Unit) {
        launch(Dispatchers.IO) {
            sleep(2000)
            if (!failedOnce) {
                failedOnce = true
                withContext(Dispatchers.Main) {
                    cb.invoke(NetworkResult.Failure(errorResponse = "faked"))
                }
                return@launch
            }

            val user = createUser(attempt)
            prefs.user = user
            withContext(Dispatchers.Main) {
                cb.invoke(NetworkResult.Success(user))
            }
        }
    }

    private fun createUser(signUpAttempt: SignUpAttempt) : User {
        return User(
            id = UUID.randomUUID().toString(),
            firstName = signUpAttempt.firstName,
            lastName = signUpAttempt.lastName,
            fullName = "${signUpAttempt.firstName} ${signUpAttempt.lastName}",
            email = signUpAttempt.credentials.email,
            bookmarks = emptyList(),
            groups = emptyList()
        )
    }

    private fun createUser(loginAttempt: LoginAttempt) : User {
        return User(
            id = UUID.randomUUID().toString(),
            firstName = "Jon",
            lastName = "Doe",
            fullName = "Jon Doe",
            email = loginAttempt.credentials.email,
            bookmarks = emptyList(),
            groups = emptyList()
        )
    }
}
