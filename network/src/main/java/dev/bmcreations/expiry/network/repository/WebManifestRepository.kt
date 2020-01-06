package dev.bmcreations.expiry.network.repository

import dev.bmcreations.expiry.models.WebManifest

interface WebManifestRepository {
    suspend fun loadManifest(url: String, jsonTarget: Boolean = false): NetworkResult<WebManifest>
}
