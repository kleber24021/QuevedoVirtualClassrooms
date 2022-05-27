package com.quevedo.virtualclassroomsserver.facade.services;

import com.quevedo.virtualclassroomsserver.common.models.dto.classroom.ClassroomGetDTO;
import com.quevedo.virtualclassroomsserver.common.models.dto.classroom.ClassroomPostDTO;
import com.quevedo.virtualclassroomsserver.common.models.dto.classroom.ClassroomPutDTO;
import io.vavr.control.Either;

import java.util.List;

public interface ClassroomServices {
    Either<String, ClassroomGetDTO> createClassroom(ClassroomPostDTO toCreate);
    Either<String, ClassroomGetDTO> editClassroom(ClassroomPutDTO toEdit);
    Either<String, List<ClassroomGetDTO>> getAllClassroomsByUserId(String userId);
    Either<String, ClassroomGetDTO> getClassroomById(String classroomId);
    Either<String, String> deleteClassroom(String classroomId);
}
