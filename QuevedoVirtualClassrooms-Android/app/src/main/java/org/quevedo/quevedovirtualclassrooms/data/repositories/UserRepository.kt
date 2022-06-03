package org.quevedo.quevedovirtualclassrooms.data.repositories

import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.quevedo.quevedovirtualclassrooms.data.models.NetworkResult
import org.quevedo.quevedovirtualclassrooms.data.models.user.UserGetDTO
import org.quevedo.quevedovirtualclassrooms.data.sources.remote.DataConsts
import org.quevedo.quevedovirtualclassrooms.data.sources.remote.RemoteDataSource
import javax.inject.Inject
import javax.inject.Named

@ActivityRetainedScoped
class UserRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    @Named(DataConsts.IO_DISPATCHER)
    private val ioDispatcher: CoroutineDispatcher
){
    fun getUserDetailByUsername(username: String): Flow<NetworkResult<UserGetDTO>> {
        return flow {
            val result = remoteDataSource.getUserDetail(username)
            emit(result)
        }.flowOn(ioDispatcher)
    }
}