package dev.bmcreations.expiry.network.repository

import retrofit2.Response

sealed class NetworkResult<out T> {
    data class Success<T>(val body: T) : NetworkResult<T>()
    data class Failure<T>(val errorResponse: String? = null) : NetworkResult<T>()
}
