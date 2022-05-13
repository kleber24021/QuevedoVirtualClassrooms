package org.quevedo.quevedovirtualclassrooms.usecases.resource

import org.quevedo.quevedovirtualclassrooms.data.repositories.ResourcesRepository
import javax.inject.Inject

class GetResourceDetailById @Inject constructor(
    private val resourcesRepository: ResourcesRepository
    ) {
    fun invoke(resourceId: String) = resourcesRepository.getResourceDetailById(resourceId);
}