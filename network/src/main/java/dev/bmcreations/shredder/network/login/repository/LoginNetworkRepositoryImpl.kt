package dev.bmcreations.shredder.network.login.repository

import dev.bmcreations.graphql.model.request.QueryContainerBuilder
import dev.bmcreations.shredder.core.extensions.exhaustive
import dev.bmcreations.shredder.core.extensions.isDebugging
import dev.bmcreations.shredder.models.User
import dev.bmcreations.shredder.models.UserLogin
import dev.bmcreations.shredder.network.NetworkResult
import dev.bmcreations.shredder.network.login.service.LoginService
import dev.bmcreations.shredder.network.user.service.UserService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class LoginNetworkRepositoryImpl(
    private val loginWebService: LoginService
): LoginRepository, CoroutineScope by CoroutineScope(Dispatchers.IO) {

    override suspend fun login(email: String, password: String): NetworkResult<User> {
        return try {
            val result = loginWebService.loginAsync(
                QueryContainerBuilder()
                    .putVariable("email", email)
                    .putVariable("password", password)
            )

            if (result.hasErrors()) {
                NetworkResult.Failure(graphErrors = result.errors)
            } else {
                val login = result.result()
                return if (login == null) {
                    NetworkResult.Failure(errorResponse = "UserLogin was null")
                } else {
                    NetworkResult.Success(result.result().exhaustive!!)
                }
            }
        } catch (e: Exception) {
            val response = if (isDebugging) e.localizedMessage else "Failed to login.\nPlease try again"
            NetworkResult.Failure(errorResponse = response)
        }
    }
}
