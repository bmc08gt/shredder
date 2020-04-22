package dev.bmcreations.shredder.network.user.repository

import dev.bmcreations.graphql.model.request.QueryContainerBuilder
import dev.bmcreations.shredder.core.extensions.exhaustive
import dev.bmcreations.shredder.models.CreateUserRequest
import dev.bmcreations.shredder.models.User
import dev.bmcreations.shredder.models.UserLogin
import dev.bmcreations.shredder.network.NetworkResult
import dev.bmcreations.shredder.network.user.service.UserService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class UserNetworkRepositoryImpl(
    private val webService: UserService
): UserRepository, CoroutineScope by CoroutineScope(Dispatchers.IO) {

    override suspend fun getUser(): NetworkResult<User> {
        val result =  webService.getUserAync(QueryContainerBuilder())

        return if (result.hasErrors()) {
            NetworkResult.Failure(graphErrors  = result.errors)
        } else {
            val login = result.result()
            return if (login == null) {
                NetworkResult.Failure(errorResponse = "UserLogin was null")
            } else {
                NetworkResult.Success(result.result().exhaustive!!)
            }
        }
    }

    override suspend fun createUser(request: CreateUserRequest): NetworkResult<User> {
        val result = webService.createUserAsync(
            QueryContainerBuilder()
                .putVariable("firstName", request.firstName)
                .putVariable("lastName", request.lastName)
                .putVariable("email", request.email)
                .putVariable("password", request.password)
        )

        return if (result.hasErrors()) {
            NetworkResult.Failure(graphErrors  = result.errors)
        } else {
            val login = result.result()
            return if (login == null) {
                NetworkResult.Failure(errorResponse = "UserLogin was null")
            } else {
                NetworkResult.Success(result.result().exhaustive!!)
            }
        }
    }
}
