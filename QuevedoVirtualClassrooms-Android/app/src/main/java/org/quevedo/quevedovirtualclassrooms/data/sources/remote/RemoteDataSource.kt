package org.quevedo.quevedovirtualclassrooms.data.sources.remote

import org.quevedo.quevedovirtualclassrooms.data.models.BaseApiResponse
import javax.inject.Inject

class RemoteDataSource @Inject
constructor(private val videoService: QuevedoVideoService) : BaseApiResponse(){
    suspend fun getAllUploadedVideos() = safeApiCall(
        apiCall = { videoService.getAllVideos() }
    )
}