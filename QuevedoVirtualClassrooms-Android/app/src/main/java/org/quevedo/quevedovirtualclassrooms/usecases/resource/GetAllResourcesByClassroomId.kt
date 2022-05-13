package org.quevedo.quevedovirtualclassrooms.usecases.resource

import org.quevedo.quevedovirtualclassrooms.data.repositories.ResourcesRepository
import javax.inject.Inject

class GetAllResourcesByClassroomId @Inject constructor(
    private val resourcesRepository: ResourcesRepository
) {
    fun invoke(classroomId: String) = resourcesRepository.getAllResourcesByClassroomId(classroomId)
}