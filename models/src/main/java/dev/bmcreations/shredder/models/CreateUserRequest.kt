package dev.bmcreations.shredder.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CreateUserRequest(
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String
): Parcelable
