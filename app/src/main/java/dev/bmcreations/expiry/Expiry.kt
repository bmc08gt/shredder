package dev.bmcreations.expiry

import android.app.Application
import android.content.Context
import dev.bmcreations.expiry.di.AppGraph
import dev.bmcreations.expiry.di.SessionGraphImpl

class Expiry : Application() {

    private val appGraph by lazy {
        AppGraph(
            sessionGraph = SessionGraphImpl(this)
        )
    }

    override fun onCreate() {
        appGraph // touch the graph
        super.onCreate()
    }
}

val Context.appGraph: AppGraph
get() = ((this.applicationContext) as Expiry).appGraph
