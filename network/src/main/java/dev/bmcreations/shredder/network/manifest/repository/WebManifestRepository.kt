package dev.bmcreations.shredder.network.manifest.repository

import dev.bmcreations.shredder.models.WebManifest
import dev.bmcreations.shredder.network.NetworkResult

interface WebManifestRepository {
    suspend fun loadManifest(url: String, jsonTarget: Boolean = false): NetworkResult<WebManifest>
}
