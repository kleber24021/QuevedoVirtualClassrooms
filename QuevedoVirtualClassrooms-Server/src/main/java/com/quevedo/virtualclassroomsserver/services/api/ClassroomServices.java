package com.quevedo.virtualclassroomsserver.services.api;

import com.quevedo.virtualclassroomsserver.common.models.dto.classroom.ClassroomGetDTO;
import io.vavr.control.Either;

import java.util.List;

public interface ClassroomServices {
    Either<String, ClassroomGetDTO> createClassroom();
    Either<String, ClassroomGetDTO> editClassroom();
    Either<String, List<ClassroomGetDTO>> getAllClassrooms(String userId);
    Either<String, ClassroomGetDTO> getClassroomById(String classroomId);
    Either<String, ClassroomGetDTO> deleteClassroom(String classroomId);
}