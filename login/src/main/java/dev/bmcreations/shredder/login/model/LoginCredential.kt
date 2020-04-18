package dev.bmcreations.shredder.login.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LoginCredential(val email: String, val password: String): Parcelable
