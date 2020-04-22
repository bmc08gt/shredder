package dev.bmcreations.shredder.network.user.service

import dev.bmcreations.graphql.annotation.GraphQuery
import dev.bmcreations.graphql.model.request.QueryContainerBuilder
import dev.bmcreations.shredder.models.CreateUserResponse
import dev.bmcreations.shredder.models.GetUserResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.Body
import retrofit2.http.POST

interface UserService {
    @POST("api")
    @GraphQuery("GetUser")
    suspend fun getUserAync(@Body query: QueryContainerBuilder): GetUserResponse

    @POST("api")
    @GraphQuery("CreateUser")
    suspend fun createUserAsync(@Body query: QueryContainerBuilder): CreateUserResponse
}
