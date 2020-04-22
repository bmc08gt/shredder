package dev.bmcreations.shredder.network

import dev.bmcreations.graphql.model.GraphQLError

sealed class NetworkResult<out T> {
    data class Success<T>(val body: T) : NetworkResult<T>()
    data class Failure<T>(val graphErrors: List<GraphQLError>? = null, val errorResponse: String? = null) : NetworkResult<T>()
}
