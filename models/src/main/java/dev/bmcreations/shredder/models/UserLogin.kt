package dev.bmcreations.shredder.models


import kotlinx.android.parcel.Parcelize
import android.os.Parcelable
import dev.bmcreations.graphql.model.GraphResult

@Parcelize
data class UserLogin(
    val user: User
) : Parcelable

class CreateUserResponse : GraphResult<CreateUserResult, UserLogin>() {
    override fun hasData(): Boolean = data?.createUser != null
    override fun result(): UserLogin? = data?.createUser
}
data class CreateUserResult(val createUser: UserLogin)

class LoginResponse : GraphResult<LoginResult, UserLogin>() {
    override fun hasData(): Boolean = data?.login != null
    override fun result(): UserLogin? = data?.login
}
data class LoginResult(val login: UserLogin)

