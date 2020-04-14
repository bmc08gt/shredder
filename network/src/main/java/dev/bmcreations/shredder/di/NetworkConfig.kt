package dev.bmcreations.shredder.di

import dev.bmcreations.shredder.core.di.Component
import dev.bmcreations.shredder.models.NetworkConfig
import dev.bmcreations.shredder.network.manifest.service.WebManifestService
import dev.bmcreations.shredder.network.manifest.repository.NetworkWebManifestRepositoryImpl
import dev.bmcreations.shredder.network.manifest.repository.WebManifestRepository
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit

interface NetworkComponent: Component {
    val retrofit: Retrofit
}

open class BaseNetworkComponent(
    networkConfig: NetworkConfig,
    converters: List<Converter.Factory> = emptyList(),
    callAdapters: List<CallAdapter.Factory> = emptyList(),
    interceptors: List<Interceptor> = emptyList()
): NetworkComponent {

    private val okHttpClientBuilder = OkHttpClient.Builder()

    init {
        interceptors.forEach { interceptor ->
            okHttpClientBuilder.addInterceptor(interceptor)
        }
    }

    override val retrofit: Retrofit = Retrofit.Builder().apply {
        baseUrl(networkConfig.fullUrl)
        converters.forEach { addConverterFactory(it) }
        callAdapters.forEach { addCallAdapterFactory(it) }
        client(okHttpClientBuilder.build())
    }.build()
}
