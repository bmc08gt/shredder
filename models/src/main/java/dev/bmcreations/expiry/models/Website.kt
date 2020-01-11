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
        get() = "https://www.google.com/s2/favicons?domain=${url}"

}

fun String.withManifest(): String {
    val url = if (!this.contains("https") && !this.contains("http")) {
        "https://$this"
    } else {
        this
    }
    return url.toHttpUrlOrNull()?.run {
        newBuilder().encodedPath("/manifest.webmanifest").build()
    }?.toString() ?: ""
}

fun String.withManifestJson(): String {
    val url = if (!this.contains("https") && !this.contains("http")) {
        "https://$this"
    } else {
        this
    }
    return url.toHttpUrlOrNull()?.run {
        newBuilder().encodedPath("/manifest.json").build()
    }?.toString() ?: ""
}
