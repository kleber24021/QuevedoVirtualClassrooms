package org.quevedo.quevedovirtualclassrooms.data.models.resource

import java.time.LocalDateTime

data class ResourceCommentDTO(
    private val usernameOwner: String,
    private val text: String,
    private val timeStamp: LocalDateTime,
    private val inAnswer: Boolean,
    private val answersTo: String,
    private val resourceUUID: String
)