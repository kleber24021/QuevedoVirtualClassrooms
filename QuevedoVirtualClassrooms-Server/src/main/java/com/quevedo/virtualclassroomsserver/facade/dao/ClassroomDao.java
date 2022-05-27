package com.quevedo.virtualclassroomsserver.facade.dao;

import com.quevedo.virtualclassroomsserver.common.models.dto.classroom.ClassroomGetDTO;
import com.quevedo.virtualclassroomsserver.common.models.dto.classroom.ClassroomPostDTO;
import com.quevedo.virtualclassroomsserver.common.models.dto.classroom.ClassroomPutDTO;
import io.vavr.control.Either;

import java.util.List;

public interface ClassroomDao {
    Either<String, ClassroomGetDTO> createClassroom(ClassroomPostDTO toCreate);
    Either<String, ClassroomGetDTO> editClassroom(ClassroomPutDTO toEdit);
    Either<String, List<ClassroomGetDTO>> getAllClassrooms(String userId);
    Either<String, ClassroomGetDTO> getClassroomById(String classroomId);
    Either<String, Integer> deleteClassroom(String classroomId);
}
