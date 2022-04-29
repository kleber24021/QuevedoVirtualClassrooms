package org.quevedo.quevedovirtualclassrooms.data.repositories

import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.quevedo.quevedovirtualclassrooms.data.models.NetworkResult
import org.quevedo.quevedovirtualclassrooms.data.models.VideoInfo
import org.quevedo.quevedovirtualclassrooms.data.sources.remote.DataConsts
import org.quevedo.quevedovirtualclassrooms.data.sources.remote.RemoteDataSource
import javax.inject.Inject
import javax.inject.Named

@ActivityRetainedScoped
class VideoRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    @Named(DataConsts.IO_DISPATCHER)
    private val ioDispatcher: CoroutineDispatcher
){
    fun getAllVideos() : Flow<NetworkResult<List<VideoInfo>>> {
        return flow {
            val result = remoteDataSource.getAllUploadedVideos()
            emit(result)
        }.flowOn(ioDispatcher)
    }
}