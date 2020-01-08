package dev.bmcreations.expiry.network.repository

import dev.bmcreations.expiry.models.WebManifest
import dev.bmcreations.expiry.models.withManifest
import dev.bmcreations.expiry.models.withManifestJson
import dev.bmcreations.expiry.network.WebManifestService

class NetworkWebManifestRepositoryImpl(
    private val webService: WebManifestService
) : WebManifestRepository {
    override suspend fun loadManifest(
        url: String,
        jsonTarget: Boolean
    ): NetworkResult<WebManifest> {
        val response = if (jsonTarget) {
            webService.loadJson(url.withManifestJson())
        } else {
            webService.loadManifest(url.withManifest())
        }.await()

        if (response.isSuccessful) {
            val manifest = response.body()
            if (manifest != null) {
                return NetworkResult.Success(manifest)
            }
        }
        return NetworkResult.Failure(response.message())
    }
}
