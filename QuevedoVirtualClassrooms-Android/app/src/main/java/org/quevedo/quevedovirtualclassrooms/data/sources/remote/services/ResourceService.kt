package org.quevedo.quevedovirtualclassrooms.data.sources.remote.services

import org.quevedo.quevedovirtualclassrooms.data.models.resource.ResourceCommentDTO
import org.quevedo.quevedovirtualclassrooms.data.models.resource.ResourceCommentPost
import org.quevedo.quevedovirtualclassrooms.data.models.resource.ResourceDetailGetDTO
import org.quevedo.quevedovirtualclassrooms.data.models.resource.ResourceLiteGetDTO
import retrofit2.Response
import retrofit2.http.*

interface ResourceService {
    @GET("resources/all")
    suspend fun getAllResourcesByClassroomId(@Query("classroomId")classroomId: String, @Query("resourceType")resourceType: String): Response<List<ResourceLiteGetDTO>>

    @GET("resources/detail/{id}")
    suspend fun getResourceDetailById(@Path("id") resourceId: String): Response<ResourceDetailGetDTO>

    @POST("resources/comment")
    suspend fun postNewComment(@Body newComment: ResourceCommentPost): Response<ResourceCommentDTO>
}