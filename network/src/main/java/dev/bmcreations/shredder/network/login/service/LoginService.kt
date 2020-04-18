package dev.bmcreations.shredder.network.login.service

import dev.bmcreations.graphql.model.request.QueryContainerBuilder
import dev.bmcreations.shredder.models.LoginResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.Body

interface LoginService {
    fun loginAsync(@Body query: QueryContainerBuilder): Deferred<LoginResponse>
}
