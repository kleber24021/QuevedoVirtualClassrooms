package org.quevedo.quevedovirtualclassrooms.ui.classroom.classroom_list

import org.quevedo.quevedovirtualclassrooms.data.models.classroom.Classroom
import org.quevedo.quevedovirtualclassrooms.data.models.resource.ResourceDetailGetDTO

interface ClassroomListContract {
    sealed class Event {
        object GetObjects : Event()
        object ErrorMostrado : Event()
        data class SetUsername(val username: String) : Event()
    }

    data class State(
        val classrooms: List<Classroom>? = null,
        val username: String = "",
        val error: String? = null
    )
}