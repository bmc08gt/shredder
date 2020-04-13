package dev.bmcreations.shredder.di

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dev.bmcreations.shredder.models.NetworkConfig
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.converter.gson.GsonConverterFactory

class WebManifestNetworkComponentImpl(
): BaseNetworkComponent(
    networkConfig = NetworkConfig(
        baseUrl = "https://bmcreations.dev" // this get's hot swapped via @Url annotation,
    ),
    interceptors = listOf(HttpLoggingInterceptor().apply { this.level = HttpLoggingInterceptor.Level.BODY }),
    converters = listOf(GsonConverterFactory.create()),
    callAdapters = listOf(CoroutineCallAdapterFactory.invoke())
)
