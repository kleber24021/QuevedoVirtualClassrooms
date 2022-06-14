package org.quevedo.quevedovirtualclassrooms.data.sources.remote.services

import org.quevedo.quevedovirtualclassrooms.data.models.user.UserGetDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface UserService {
    @GET("users/{username}")
    suspend fun getUserByUsername(@Path("username") username: String): Response<UserGetDTO>

}