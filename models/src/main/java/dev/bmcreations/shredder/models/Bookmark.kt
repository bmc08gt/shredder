package dev.bmcreations.shredder.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Bookmark(
    val id: String = UUID.randomUUID().toString(),
    val site: Website,
    val label: String,
    val expiresAt: Date? = null,
    val group: Group? = null,
    val createdAt: Date? = Date()
) : Parcelable
