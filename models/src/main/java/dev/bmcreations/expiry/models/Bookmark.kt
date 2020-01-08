package dev.bmcreations.expiry.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Bookmark(
    val id: String = UUID.randomUUID().toString(),
    val site: Website?,
    val title: String?,
    val expiration: Date? = null
) : Parcelable
