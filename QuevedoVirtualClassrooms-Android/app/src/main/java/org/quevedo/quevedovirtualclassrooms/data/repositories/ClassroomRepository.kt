package org.quevedo.quevedovirtualclassrooms.data.repositories

import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.quevedo.quevedovirtualclassrooms.data.models.NetworkResult
import org.quevedo.quevedovirtualclassrooms.data.models.classroom.Classroom
import org.quevedo.quevedovirtualclassrooms.data.sources.remote.DataConsts
import org.quevedo.quevedovirtualclassrooms.data.sources.remote.RemoteDataSource
import javax.inject.Inject
import javax.inject.Named

@ActivityRetainedScoped
class ClassroomRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    @Named(DataConsts.IO_DISPATCHER)
    private val ioDispatcher: CoroutineDispatcher
){
    fun getAllClassroomsByUsername(username: String): Flow<NetworkResult<List<Classroom>>> {
        return flow {
            val result = remoteDataSource.getAllClassroomsByUsername(username)
            emit(result)
        }.flowOn(ioDispatcher)
    }
}