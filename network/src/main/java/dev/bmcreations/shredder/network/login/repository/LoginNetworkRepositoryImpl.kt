package dev.bmcreations.shredder.network.login.repository

import dev.bmcreations.shredder.models.UserLogin
import dev.bmcreations.shredder.network.NetworkResult
import dev.bmcreations.shredder.network.login.service.LoginService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class LoginNetworkRepositoryImpl(
    private val webService: LoginService
): LoginRepository, CoroutineScope by CoroutineScope(Dispatchers.IO) {

    override suspend fun login(email: String, password: String): NetworkResult<UserLogin> {
        return NetworkResult.Failure()
    }
}
