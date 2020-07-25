package dev.bmcreations.shredder.network.sync.repository

import dev.bmcreations.shredder.models.Bookmark
import dev.bmcreations.shredder.models.DeleteBookmarkResult
import dev.bmcreations.shredder.models.Group
import dev.bmcreations.shredder.network.NetworkResult
import java.util.*

interface BookmarkSyncRepository {
    suspend fun createBookmark(
        label: String,
        icon: String,
        url: String,
        expiresAt: Date,
        groupId: String
    ): NetworkResult<Bookmark>

    suspend fun upsertBookmark(
        id: String,
        label: String,
        icon: String,
        url: String,
        expiresAt: Date,
        archived: Boolean
    ): NetworkResult<Bookmark>

    suspend fun deleteBookmark(
        id: String
    ): NetworkResult<Boolean>

    suspend fun createGroup(
        label: String
    ): NetworkResult<Group>

    suspend fun upsertGroup(
        id: String,
        label: String,
        archived: Boolean
    ): NetworkResult<Group>

    suspend fun deleteGroup(
        id: String
    ): NetworkResult<Boolean>
}
