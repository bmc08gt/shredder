package dev.bmcreations.shredder.network.manifest.repository

import dev.bmcreations.shredder.models.WebManifest
import dev.bmcreations.shredder.models.withManifest
import dev.bmcreations.shredder.models.withManifestJson
import dev.bmcreations.shredder.network.NetworkResult
import dev.bmcreations.shredder.network.manifest.service.WebManifestService
import java.lang.Exception

class NetworkWebManifestRepositoryImpl(
    private val webService: WebManifestService
) : WebManifestRepository {
    override suspend fun loadManifest(
        url: String,
        jsonTarget: Boolean
    ): NetworkResult<WebManifest> {
        try {
            val manifestResponse = webService.loadManifest(url.withManifest()).await()
            var errorMessage: String? = null
            if (manifestResponse.isSuccessful) {
                val manifest = manifestResponse.body()
                if (manifest != null) {
                    return NetworkResult.Success(manifest)
                }
            } else {
                val jsonResponse = webService.loadJson(url.withManifestJson()).await()
                if (jsonResponse.isSuccessful) {
                    val manifest = jsonResponse.body()
                    if (manifest != null) {
                        return NetworkResult.Success(manifest)
                    }
                } else {
                    errorMessage = jsonResponse.message()
                }
            }
            return NetworkResult.Failure(errorResponse = errorMessage)
        } catch (e: Exception) {
            return NetworkResult.Failure(errorResponse = e.localizedMessage)
        }
    }
}
