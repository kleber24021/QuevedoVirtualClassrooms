package org.quevedo.quevedovirtualclassrooms.data.models.resource

import java.time.LocalDateTime

data class ResourceCommentDTO(
    val idComment: Int,
    val usernameOwner: String,
    val text: String,
    val timeStamp: LocalDateTime,
    val answersTo: String,
)