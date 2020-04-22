package dev.bmcreations.shredder.network.login.service

import dev.bmcreations.graphql.annotation.GraphQuery
import dev.bmcreations.graphql.model.request.QueryContainerBuilder
import dev.bmcreations.shredder.models.CreateUserResponse
import dev.bmcreations.shredder.models.LoginResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginService {
    @POST("api")
    @GraphQuery("Login")
    suspend fun loginAsync(@Body query: QueryContainerBuilder): LoginResponse
}
