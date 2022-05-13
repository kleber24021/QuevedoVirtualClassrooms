package org.quevedo.quevedovirtualclassrooms.data.models.classroom

data class Classroom(
    val uuidClassroom: String,
    val name: String,
    val course: String,
    val adminUsername: String,
    val studentsUsername: List<String>
)