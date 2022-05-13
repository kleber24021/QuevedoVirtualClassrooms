package org.quevedo.quevedovirtualclassrooms.data.models.resource

import org.quevedo.quevedovirtualclassrooms.data.models.common.ResourceType
import java.time.LocalDateTime

data class ResourceLiteGetDTO(
    val uuidResource: String,
    val resourceName: String,
    val resourceUrl: String,
    val timestamp: LocalDateTime,
    val resourceType: ResourceType
)
