package dev.bmcreations.shredder.network.user.repository

import dev.bmcreations.shredder.models.CreateUserRequest
import dev.bmcreations.shredder.models.User
import dev.bmcreations.shredder.models.UserLogin
import dev.bmcreations.shredder.network.NetworkResult
import dev.bmcreations.shredder.network.user.service.UserService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class UserRepositoryImpl(
    private val webService: UserService
): UserRepository, CoroutineScope by CoroutineScope(Dispatchers.IO) {

    override suspend fun getUser(): NetworkResult<User> {
        return NetworkResult.Failure()
    }

    override suspend fun createUser(request: CreateUserRequest): NetworkResult<UserLogin> {
        return NetworkResult.Failure()
    }
}
