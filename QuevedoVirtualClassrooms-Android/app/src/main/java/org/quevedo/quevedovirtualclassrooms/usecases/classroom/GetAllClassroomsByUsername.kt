package org.quevedo.quevedovirtualclassrooms.usecases.classroom

import org.quevedo.quevedovirtualclassrooms.data.repositories.ClassroomRepository
import javax.inject.Inject

class GetAllClassroomsByUsername @Inject
constructor(
    private val classroomRepository: ClassroomRepository
) {
    fun invoke(username: String) = classroomRepository.getAllClassroomsByUsername(username)
}