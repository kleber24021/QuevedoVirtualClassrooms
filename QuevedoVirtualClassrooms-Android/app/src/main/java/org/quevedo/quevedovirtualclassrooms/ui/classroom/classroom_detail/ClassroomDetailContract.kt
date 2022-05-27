package org.quevedo.quevedovirtualclassrooms.ui.classroom.classroom_detail

import org.quevedo.quevedovirtualclassrooms.data.models.classroom.Classroom

interface ClassroomDetailContract {
    sealed class Event{
        object GetObjects : Event()
        object ErrorMostrado : Event()
        data class setClassroomId(val classroomId: String) : Event()
    }

    data class State(
        val classroom : Classroom? = null,
        val classroomId: String? = null,
        val error: String? = null
        )
}