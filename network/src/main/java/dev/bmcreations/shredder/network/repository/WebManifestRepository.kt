package dev.bmcreations.shredder.network.repository

import dev.bmcreations.shredder.models.WebManifest

interface WebManifestRepository {
    suspend fun loadManifest(url: String, jsonTarget: Boolean = false): NetworkResult<WebManifest>
}
