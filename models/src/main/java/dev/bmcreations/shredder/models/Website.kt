package dev.bmcreations.shredder.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull

@Parcelize
data class Website(
    val url: String
) : Parcelable

val Website.favicon: String
    get() {
        return "https://www.google.com/s2/favicons?domain=${url}"
    }

private val Website.urlWithScheme: String
    get() {
        return when {
            !url.contains("https") && !url.contains("http") -> "https://$url"
            else -> url
        }
    }

fun Website.withManifest(): String {
    return urlWithScheme.toHttpUrlOrNull()?.run {
        newBuilder().encodedPath("/manifest.webmanifest").build()
    }?.toString() ?: ""
}

fun Website.withManifestJson(): String {
    return urlWithScheme.toHttpUrlOrNull()?.run {
        newBuilder().encodedPath("/manifest.json").build()
    }?.toString() ?: ""
}
