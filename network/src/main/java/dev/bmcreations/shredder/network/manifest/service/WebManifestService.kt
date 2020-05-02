package dev.bmcreations.shredder.network.manifest.service

import dev.bmcreations.shredder.models.WebManifest
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface WebManifestService {

    @GET
    suspend fun loadManifest(@Url url: String): WebManifest?

    @GET
    suspend fun loadJson(@Url url: String): WebManifest?
}
