package org.quevedo.quevedovirtualclassrooms.data.sources.remote

import org.quevedo.quevedovirtualclassrooms.data.models.BaseApiResponse
import org.quevedo.quevedovirtualclassrooms.data.models.resource.ResourceCommentPost
import org.quevedo.quevedovirtualclassrooms.data.sources.remote.services.ClassroomService
import org.quevedo.quevedovirtualclassrooms.data.sources.remote.services.ResourceService
import org.quevedo.quevedovirtualclassrooms.data.sources.remote.services.UserService
import javax.inject.Inject

class RemoteDataSource
@Inject
constructor(
    private val resourceService: ResourceService,
    private val classroomService: ClassroomService,
    private val userService: UserService
) : BaseApiResponse() {
    suspend fun getAllResourcesByClassroomId(classroomId: String, resourceType: String) =
        safeApiCall(
            apiCall = { resourceService.getAllResourcesByClassroomId(classroomId, resourceType) }
        )

    suspend fun getResourceDetailById(resourceId: String) = safeApiCall(
        apiCall = { resourceService.getResourceDetailById(resourceId) }
    )

    suspend fun postNewComment(newCommentPut: ResourceCommentPost) = safeApiCall(
        apiCall = { resourceService.postNewComment(newCommentPut) }
    )

    suspend fun getAllClassroomsByUsername(username: String) = safeApiCall(
        apiCall = { classroomService.getAllClassroomsByUsername(username) }
    )

    suspend fun getUserDetail(username: String) = safeApiCall(
        apiCall = { userService.getUserByUsername(username) }
    )
}