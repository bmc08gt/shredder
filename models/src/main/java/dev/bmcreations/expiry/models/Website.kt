package dev.bmcreations.expiry.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull


@Parcelize
data class Website(
    val url: String
) : Parcelable {

    var favicon: String? = null
        private set
        get() = "https://www.google.com/s2/favicons?domain=${url.toHttpUrlOrNull()?.host}"

}

fun String.withManifest() =
    this.toHttpUrlOrNull()?.run {
        newBuilder().encodedPath("/manifest.webmanifest").build()
    }?.toString() ?: ""

fun String.withManifestJson() =
    this.toHttpUrlOrNull()?.run {
        newBuilder().encodedPath("/manifest.json").build()
    }?.toString() ?: ""
