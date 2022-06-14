package org.quevedo.quevedovirtualclassrooms.usecases.resource

import org.quevedo.quevedovirtualclassrooms.data.models.resource.ResourceCommentPost
import org.quevedo.quevedovirtualclassrooms.data.repositories.ResourcesRepository
import javax.inject.Inject

class PostNewComment @Inject constructor(
    private val resourcesRepository: ResourcesRepository
) {
    fun invoke(newCommentPut: ResourceCommentPost) = resourcesRepository.postNewComment(newCommentPut)
}