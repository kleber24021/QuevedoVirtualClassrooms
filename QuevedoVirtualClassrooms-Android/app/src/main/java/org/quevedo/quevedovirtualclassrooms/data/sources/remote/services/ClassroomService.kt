package org.quevedo.quevedovirtualclassrooms.data.sources.remote.services

import org.quevedo.quevedovirtualclassrooms.data.models.classroom.Classroom
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ClassroomService {
    @GET("classrooms")
    suspend fun getAllClassroomsByUsername(@Query("username")username: String): Response<List<Classroom>>
}