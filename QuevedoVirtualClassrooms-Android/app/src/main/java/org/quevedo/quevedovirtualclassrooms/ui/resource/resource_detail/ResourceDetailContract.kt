package org.quevedo.quevedovirtualclassrooms.ui.resource.resource_detail

import org.quevedo.quevedovirtualclassrooms.data.models.resource.ResourceCommentDTO
import org.quevedo.quevedovirtualclassrooms.data.models.resource.ResourceDetailGetDTO

interface ResourceDetailContract {
    sealed class Event {
        object GetObject : Event()
        data class SendComment(val text: String, val answersTo: Int?) : Event()
        object ErrorMostrado : Event()
        data class SetResourceId(val resourceId: String) : Event()
    }

    data class State(
        val resource: ResourceDetailGetDTO? = null,
        val comments: MutableList<ResourceCommentDTO> = mutableListOf(),
        val resourceId: String = "",
        val error: String? = null
    )
}