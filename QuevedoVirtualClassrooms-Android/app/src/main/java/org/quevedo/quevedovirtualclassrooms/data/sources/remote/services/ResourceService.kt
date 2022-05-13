package org.quevedo.quevedovirtualclassrooms.data.sources.remote.services

import org.quevedo.quevedovirtualclassrooms.data.models.resource.ResourceDetailGetDTO
import org.quevedo.quevedovirtualclassrooms.data.models.resource.ResourceLiteGetDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ResourceService {
    @GET("resources/all")
    suspend fun getAllResourcesByClassroomId(@Query("classroomId")classroomId: String): Response<List<ResourceLiteGetDTO>>

    @GET("resources/detail/{id}")
    suspend fun getResourceDetailById(@Path("id") resourceId: String): Response<ResourceDetailGetDTO>
}