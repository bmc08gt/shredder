package dev.bmcreations.shredder.network

import retrofit2.Response

interface RequestResolver {
    suspend fun <T> handleResponse(
        response: () -> Response<T>
    ): NetworkResult<T> {
        try {
            val res = response.invoke()
            if (res.isSuccessful) {
                val body = res.body()
                if (body != null) {
                    return NetworkResult.Success(body)
                }
            }
            return NetworkResult.Failure(errorResponse = res.message())
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            return NetworkResult.Failure(errorResponse = e.toString())
        }
    }
}
