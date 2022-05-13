package org.quevedo.quevedovirtualclassrooms.ui.resource.resource_detail

import org.quevedo.quevedovirtualclassrooms.data.models.resource.ResourceDetailGetDTO

interface ResourceDetailContract {
    sealed class Event {
        object GetObject : Event()
        object ErrorMostrado : Event()
        data class SetResourceId(val resourceId: String) : Event()
    }

    data class State(
        val resource: ResourceDetailGetDTO? = null,
        val resourceId: String = "",
        val error: String? = null
    )
}