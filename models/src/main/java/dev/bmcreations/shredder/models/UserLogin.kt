package dev.bmcreations.shredder.models


import kotlinx.android.parcel.Parcelize
import android.os.Parcelable
import dev.bmcreations.graphql.model.GraphResult

@Parcelize
data class UserLogin(
    val user: User
) : Parcelable

class CreateUserResponse : GraphResult<CreateUserResult, User>() {
    override fun hasData(): Boolean = data?.createUser != null
    override fun result(): User? = data?.createUser
}
data class CreateUserResult(val createUser: User)

class LoginResponse : GraphResult<LoginResult, User>() {
    override fun hasData(): Boolean = data?.login != null
    override fun result(): User? = data?.login
}
data class LoginResult(val login: User)

