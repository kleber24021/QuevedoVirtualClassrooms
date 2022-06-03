package org.quevedo.quevedovirtualclassrooms.data.models.resource

import org.quevedo.quevedovirtualclassrooms.data.models.common.ResourceType
import java.time.LocalDateTime

data class ResourceDetailGetDTO(
    val uuidResource: String,
    val resourceName: String,
    var resourceUrl: String,
    val timestamp: LocalDateTime,
    val classroomUUID: String,
    val resourceType: ResourceType,
    val comments: List<ResourceCommentDTO>
)