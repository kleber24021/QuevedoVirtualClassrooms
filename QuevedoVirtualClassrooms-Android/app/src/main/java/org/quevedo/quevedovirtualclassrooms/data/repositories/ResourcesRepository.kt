package org.quevedo.quevedovirtualclassrooms.data.repositories

import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.quevedo.quevedovirtualclassrooms.data.models.NetworkResult
import org.quevedo.quevedovirtualclassrooms.data.models.resource.ResourceDetailGetDTO
import org.quevedo.quevedovirtualclassrooms.data.models.resource.ResourceLiteGetDTO
import org.quevedo.quevedovirtualclassrooms.data.sources.remote.DataConsts
import org.quevedo.quevedovirtualclassrooms.data.sources.remote.RemoteDataSource
import javax.inject.Inject
import javax.inject.Named

@ActivityRetainedScoped
class ResourcesRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    @Named(DataConsts.IO_DISPATCHER)
    private val ioDispatcher: CoroutineDispatcher
){
    fun getAllResourcesByClassroomId(classroomId: String) : Flow<NetworkResult<List<ResourceLiteGetDTO>>> {
        return flow {
            val result = remoteDataSource.getAllResourcesByClassroomId(classroomId)
            emit(result)
        }.flowOn(ioDispatcher)
    }

    fun getResourceDetailById(resourceId: String): Flow<NetworkResult<ResourceDetailGetDTO>>{
        return flow {
            val result = remoteDataSource.getResourceDetailById(resourceId)
            emit(result)
        }.flowOn(ioDispatcher)
    }
}