package dev.bmcreations.shredder.network.login.repository

import dev.bmcreations.shredder.models.User
import dev.bmcreations.shredder.network.NetworkResult

interface LoginRepository {
    suspend fun login(email: String, password: String): NetworkResult<User>
}
