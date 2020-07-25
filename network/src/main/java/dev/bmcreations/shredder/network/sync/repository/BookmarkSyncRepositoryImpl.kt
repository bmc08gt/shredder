package dev.bmcreations.shredder.network.sync.repository

import dev.bmcreations.graphql.model.request.QueryContainerBuilder
import dev.bmcreations.shredder.models.Bookmark
import dev.bmcreations.shredder.models.Group
import dev.bmcreations.shredder.network.NetworkResult
import dev.bmcreations.shredder.network.RequestResolver
import dev.bmcreations.shredder.network.sync.service.BookmarkSyncService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import java.util.*

class BookmarkSyncRepositoryImpl(
    private val webService: BookmarkSyncService
): BookmarkSyncRepository, RequestResolver, CoroutineScope by CoroutineScope(Dispatchers.IO) {
    override suspend fun createBookmark(
        label: String,
        icon: String,
        url: String,
        expiresAt: Date,
        groupId: String
    ): NetworkResult<Bookmark> {
        val result =  webService.createBookmarkAsync(
            with(QueryContainerBuilder()) {
                putVariable("label", label)
                putVariable("icon", icon)
                putVariable("url", url)
                putVariable("expiresAt", expiresAt)
                putVariable("groupId", groupId)
            }
        )

        return if (result.hasErrors()) {
            NetworkResult.Failure(graphErrors  = result.errors)
        } else {
            val bookmark = result.result()
            return if (bookmark == null) {
                NetworkResult.Failure(errorResponse = "bookmark create failed to sync")
            } else {
                NetworkResult.Success(bookmark)
            }
        }
    }

    override suspend fun upsertBookmark(
        id: String,
        label: String,
        icon: String,
        url: String,
        expiresAt: Date,
        archived: Boolean
    ): NetworkResult<Bookmark> {
        val result =  webService.upsertBookmarkAsync(
            with(QueryContainerBuilder()) {
                putVariable("id", id)
                putVariable("label", label)
                putVariable("icon", icon)
                putVariable("url", url)
                putVariable("expiresAt", expiresAt)
                putVariable("archived", archived)
            }
        )

        return if (result.hasErrors()) {
            NetworkResult.Failure(graphErrors  = result.errors)
        } else {
            val bookmark = result.result()
            return if (bookmark == null) {
                NetworkResult.Failure(errorResponse = "bookmark upsert failed to sync")
            } else {
                NetworkResult.Success(bookmark)
            }
        }
    }

    override suspend fun deleteBookmark(id: String): NetworkResult<Boolean> {
        val result =  webService.deleteBookmarkAsync(
            with(QueryContainerBuilder()) {
                putVariable("id", id)
            }
        )

        return if (result.hasErrors()) {
            NetworkResult.Failure(graphErrors  = result.errors)
        } else {
            val deleted = result.result()
            return if (deleted == null) {
                NetworkResult.Failure(errorResponse = "bookmark delete failed to sync")
            } else {
                NetworkResult.Success(deleted)
            }
        }
    }

    override suspend fun createGroup(label: String): NetworkResult<Group> {
        val result =  webService.createGroupAsync(
            with(QueryContainerBuilder()) {
                putVariable("label", label)
            }
        )

        return if (result.hasErrors()) {
            NetworkResult.Failure(graphErrors  = result.errors)
        } else {
            val group = result.result()
            return if (group == null) {
                NetworkResult.Failure(errorResponse = "group create failed to sync")
            } else {
                NetworkResult.Success(group)
            }
        }
    }

    override suspend fun upsertGroup(
        id: String,
        label: String,
        archived: Boolean
    ): NetworkResult<Group> {
        val result =  webService.upsertGroupAsync(
            with(QueryContainerBuilder()) {
                putVariable("id", id)
                putVariable("label", label)
                putVariable("archived", archived)
            }
        )

        return if (result.hasErrors()) {
            NetworkResult.Failure(graphErrors  = result.errors)
        } else {
            val group = result.result()
            return if (group == null) {
                NetworkResult.Failure(errorResponse = "group upsert failed to sync")
            } else {
                NetworkResult.Success(group)
            }
        }
    }

    override suspend fun deleteGroup(id: String): NetworkResult<Boolean> {
        val result =  webService.deleteGroupAsync(
            with(QueryContainerBuilder()) {
                putVariable("id", id)
            }
        )

        return if (result.hasErrors()) {
            NetworkResult.Failure(graphErrors  = result.errors)
        } else {
            val deleted = result.result()
            return if (deleted == null) {
                NetworkResult.Failure(errorResponse = "group delete failed to sync")
            } else {
                NetworkResult.Success(deleted)
            }
        }
    }
}
