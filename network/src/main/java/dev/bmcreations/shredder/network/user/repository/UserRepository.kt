package dev.bmcreations.shredder.network.user.repository

import dev.bmcreations.shredder.models.CreateUserRequest
import dev.bmcreations.shredder.models.User
import dev.bmcreations.shredder.models.UserLogin
import dev.bmcreations.shredder.network.NetworkResult

interface UserRepository {
    suspend fun getUser(): NetworkResult<User>
    suspend fun createUser(request: CreateUserRequest): NetworkResult<UserLogin>
}
