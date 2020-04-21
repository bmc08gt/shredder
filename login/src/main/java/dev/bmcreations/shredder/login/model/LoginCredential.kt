package dev.bmcreations.shredder.login.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LoginCredential(val email: String, val password: String): Parcelable

sealed class AuthAttempt
@Parcelize
data class SignUpAttempt(
    val firstName: String,
    val lastName: String,
    val credentials: LoginCredential = LoginCredential("", ""),
    val success: Boolean = false
): AuthAttempt(), Parcelable

@Parcelize
data class LoginAttempt(
    val credentials: LoginCredential = LoginCredential("", ""),
    val success: Boolean = false
): AuthAttempt(), Parcelable
