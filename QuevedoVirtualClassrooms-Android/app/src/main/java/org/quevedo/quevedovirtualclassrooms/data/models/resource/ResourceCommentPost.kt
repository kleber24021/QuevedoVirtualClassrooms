package org.quevedo.quevedovirtualclassrooms.data.models.resource

data class ResourceCommentPost(
    val text: String,
    val username: String,
    val answersTo: Int?,
    val resourceId: String
)