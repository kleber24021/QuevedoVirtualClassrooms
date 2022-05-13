package org.quevedo.quevedovirtualclassrooms.data.sources.remote

import org.quevedo.quevedovirtualclassrooms.data.models.BaseApiResponse
import org.quevedo.quevedovirtualclassrooms.data.sources.remote.services.ClassroomService
import org.quevedo.quevedovirtualclassrooms.data.sources.remote.services.ResourceService
import javax.inject.Inject

class RemoteDataSource
@Inject
constructor(
    private val resourceService: ResourceService,
    private val classroomService: ClassroomService
    ) : BaseApiResponse(){
    suspend fun getAllResourcesByClassroomId(classroomId: String) = safeApiCall(
        apiCall = { resourceService.getAllResourcesByClassroomId(classroomId) }
    )
    suspend fun getResourceDetailById(resourceId: String) = safeApiCall(
        apiCall = {resourceService.getResourceDetailById(resourceId) }
    )
    suspend fun getAllClassroomsByUsername(username: String) = safeApiCall(
        apiCall = {classroomService.getAllClassroomsByUsername(username)}
    )
}