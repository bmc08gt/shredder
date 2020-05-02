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
            val manifestResponse = webService.loadManifest(url.withManifest())
            return if (manifestResponse != null) {
                NetworkResult.Success(manifestResponse)
            } else {
                NetworkResult.Failure(errorResponse = "Failed to pull manifest response for icon")
            }
        } catch (e: Exception) {
            return try {
                val jsonResponse = webService.loadJson(url.withManifestJson())
                if (jsonResponse != null) {
                    NetworkResult.Success(jsonResponse)
                } else {
                    NetworkResult.Failure(errorResponse = "Failed to pull manifest response for icon")
                }
            } catch (e: Exception) {
                NetworkResult.Failure(errorResponse = e.localizedMessage)
            }
        }
    }
}
