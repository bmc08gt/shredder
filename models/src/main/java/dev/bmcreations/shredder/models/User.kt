package dev.bmcreations.shredder.models

import android.os.Parcelable
import dev.bmcreations.graphql.model.GraphResult
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    val id: String,
    val firstName: String,
    val lastName: String,
    val fullName: String,
    val email: String,
    val bookmarks: List<Bookmark>,
    val groups: List<Group>
) : Parcelable

class GetUserResponse : GraphResult<GetUserResult, User>() {
    override fun hasData(): Boolean = data?.me != null
    override fun result(): User? = data?.me
}
data class GetUserResult(val me: User)
