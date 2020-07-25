package dev.bmcreations.shredder.network.sync.service

import dev.bmcreations.graphql.annotation.GraphQuery
import dev.bmcreations.graphql.model.request.QueryContainerBuilder
import dev.bmcreations.shredder.models.*
import retrofit2.http.Body
import retrofit2.http.POST

interface BookmarkSyncService {
    @POST("api")
    @GraphQuery("CreateBookmark")
    fun createBookmarkAsync(@Body query: QueryContainerBuilder): CreateBookmarkResponse

    @POST("api")
    @GraphQuery("UpdateBookmark")
    fun upsertBookmarkAsync(@Body query: QueryContainerBuilder): UpdateBookmarkResponse

    @POST("api")
    @GraphQuery("DeleteBookmark")
    fun deleteBookmarkAsync(@Body query: QueryContainerBuilder): DeleteBookmarkResponse

    @POST("api")
    @GraphQuery("CreateGroup")
    fun createGroupAsync(@Body query: QueryContainerBuilder): CreateGroupResponse

    @POST("api")
    @GraphQuery("UpdateGroup")
    fun upsertGroupAsync(@Body query: QueryContainerBuilder): UpdateGroupResponse

    @POST("api")
    @GraphQuery("DeleteGroup")
    fun deleteGroupAsync(@Body query: QueryContainerBuilder): DeleteGroupResponse
}
