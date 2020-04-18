package dev.bmcreations.shredder.network.login.repository

import dev.bmcreations.shredder.models.UserLogin
import dev.bmcreations.shredder.network.NetworkResult

interface LoginRepository {
    suspend fun login(email: String, password: String): NetworkResult<UserLogin>
}
