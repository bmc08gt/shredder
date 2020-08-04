package dev.bmcreations.shredder.home.data.models

import dev.bmcreations.shredder.models.Bookmark
import dev.bmcreations.shredder.models.Website
import java.util.*

data class EditModel(val id: String? = null, val label: String?, val url: String?, val createdAt: Date? = null) {
    fun isValid(): Boolean {
        return label.isNullOrEmpty().not() && url.isNullOrEmpty().not()
    }
}

sealed class UiState<out T> {
    object Loading : UiState<Nothing>()
    data class Success<out T>(val data: T?) : UiState<T>()
    data class Error(val exception: Throwable?) : UiState<Nothing>()
}

fun EditModel.asBookmark(): Bookmark {
    return when (id) {
        null -> Bookmark(label = label.orEmpty(), site = Website(url.orEmpty()), createdAt = Date())
        else -> Bookmark(id = id, label = label.orEmpty(), site = Website(url.orEmpty()), createdAt = createdAt)
    }
}

fun Bookmark?.edit(): EditModel {
    return EditModel(id = this?.id, label = this?.label, url = this?.site?.url, createdAt = this?.createdAt)
}
