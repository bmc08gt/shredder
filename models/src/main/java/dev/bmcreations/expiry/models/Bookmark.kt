package dev.bmcreations.expiry.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import java.util.*

@Parcelize
data class Bookmark(
    val id: String = UUID.randomUUID().toString(),
    val url: String,
    val label: String?,
    val expiration: Date
) : Parcelable {

    var favicon: String? = null
        private set
        get() = url.toHttpUrlOrNull()?.host?.plus("/favicon.ico")
}
