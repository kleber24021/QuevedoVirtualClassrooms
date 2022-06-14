package org.quevedo.quevedovirtualclassrooms.ui.resource.resource_watcher

import org.quevedo.quevedovirtualclassrooms.data.models.resource.ResourceDetailGetDTO

interface ResourceWatcherContract{
    sealed class Event{
        data class SetResourceId(val resourceUUID: String) : Event()
        object GetObject : Event()
        object ErrorMostrado : Event()
    }

    data class State(
        val resource: ResourceDetailGetDTO? = null,
        val resourceId: String = "",
        val error: String? = null
    )
}

