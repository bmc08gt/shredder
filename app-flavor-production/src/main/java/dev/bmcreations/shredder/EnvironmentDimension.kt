package dev.bmcreations.shredder

import android.app.Application
import com.google.gson.Gson
import dev.bmcreations.graphql.converter.GraphQLConverter
import dev.bmcreations.shredder.di.BaseNetworkComponent
import dev.bmcreations.shredder.di.NetworkComponent
import dev.bmcreations.shredder.models.NetworkConfig
import retrofit2.converter.gson.GsonConverterFactory

fun Application.environmentDimensionNetworkComponent(): NetworkComponent {
    return BaseNetworkComponent(
        networkConfig = NetworkConfig(
            baseUrl = "https://shredder.mcan.sh/api"
        ),
        converters = listOf(GraphQLConverter.create(this), GsonConverterFactory.create(Gson()))
    )
}
