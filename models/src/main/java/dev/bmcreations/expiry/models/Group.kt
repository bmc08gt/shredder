package dev.bmcreations.expiry.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Group(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val createdAt: Date = Date(),
    val updatedAt: Date = createdAt,
    val bookmarks: List<Bookmark> = emptyList()
): Parcelable
