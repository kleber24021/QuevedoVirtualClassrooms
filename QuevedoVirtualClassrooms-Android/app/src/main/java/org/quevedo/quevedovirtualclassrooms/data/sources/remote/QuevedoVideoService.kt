package org.quevedo.quevedovirtualclassrooms.data.sources.remote

import org.quevedo.quevedovirtualclassrooms.data.models.VideoInfo
import retrofit2.Response
import retrofit2.http.GET

interface QuevedoVideoService {
    @GET("quevedo/videos")
    suspend fun getAllVideos(): Response<List<VideoInfo>>
}