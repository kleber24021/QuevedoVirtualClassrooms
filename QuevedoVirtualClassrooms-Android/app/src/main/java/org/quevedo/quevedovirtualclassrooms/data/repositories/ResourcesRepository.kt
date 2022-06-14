package org.quevedo.quevedovirtualclassrooms.data.repositories

import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.quevedo.quevedovirtualclassrooms.data.models.NetworkResult
import org.quevedo.quevedovirtualclassrooms.data.models.resource.ResourceCommentDTO
import org.quevedo.quevedovirtualclassrooms.data.models.resource.ResourceCommentPost
import org.quevedo.quevedovirtualclassrooms.data.models.resource.ResourceDetailGetDTO
import org.quevedo.quevedovirtualclassrooms.data.models.resource.ResourceLiteGetDTO
import org.quevedo.quevedovirtualclassrooms.data.sources.remote.DataConsts
import org.quevedo.quevedovirtualclassrooms.data.sources.remote.RemoteDataSource
import org.quevedo.quevedovirtualclassrooms.data.sources.remote.SessionManager
import javax.inject.Inject
import javax.inject.Named

@ActivityRetainedScoped
class ResourcesRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    @Named(DataConsts.IO_DISPATCHER)
    private val ioDispatcher: CoroutineDispatcher,
    private val sessionManager: SessionManager
) {
    fun getAllResourcesByClassroomId(
        classroomId: String,
        resourceType: String
    ): Flow<NetworkResult<List<ResourceLiteGetDTO>>> {
        return flow {
            val result = remoteDataSource.getAllResourcesByClassroomId(classroomId, resourceType)
            emit(result)
        }.flowOn(ioDispatcher)
    }

    fun getResourceDetailById(resourceId: String): Flow<NetworkResult<ResourceDetailGetDTO>> {
        return flow {
            val result = remoteDataSource.getResourceDetailById(resourceId)
            if (result.data != null) {
                val endpoint = result.data.resourceEndpoint
                result.data.resourceEndpoint = DataConsts.BASE_URL + "resources/file/" + endpoint + "?username=" + sessionManager.username
            }
            emit(result)
        }.flowOn(ioDispatcher)
    }

    fun postNewComment(newCommentPut: ResourceCommentPost): Flow<NetworkResult<ResourceCommentDTO>>{
        return flow {
            val result = remoteDataSource.postNewComment(newCommentPut)
            emit(result)
        }.flowOn(ioDispatcher)
    }
}