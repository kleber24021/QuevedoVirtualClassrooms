package org.quevedo.quevedovirtualclassrooms.ui.resource.resource_list

import org.quevedo.quevedovirtualclassrooms.data.models.resource.ResourceLiteGetDTO

interface ResourceListContract {
    sealed class Event {
        object GetData : Event()
        object ErrorMostrado : Event()
        data class SetClassroomId(val classroomId: String): Event()
        data class SelectVideo(val selectedVideo: ResourceLiteGetDTO) : Event()
    }

    data class State(
        val videosList: List<ResourceLiteGetDTO>? = emptyList(),
        val actualClassroomId: String = "",
        val selectedVideo: ResourceLiteGetDTO? = null,
        val error: String? = null
    )
}