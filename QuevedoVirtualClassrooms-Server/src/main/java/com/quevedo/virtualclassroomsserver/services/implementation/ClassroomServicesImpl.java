package com.quevedo.virtualclassroomsserver.services.implementation;

import com.quevedo.virtualclassroomsserver.common.models.dto.classroom.ClassroomGetDTO;
import com.quevedo.virtualclassroomsserver.dao.api.ClassroomDao;
import com.quevedo.virtualclassroomsserver.services.api.ClassroomServices;
import io.vavr.control.Either;
import jakarta.inject.Inject;

import java.util.List;

public class ClassroomServicesImpl implements ClassroomServices {

    private final ClassroomDao classroomDao;

    @Inject
    public ClassroomServicesImpl(ClassroomDao classroomDao) {
        this.classroomDao = classroomDao;
    }

    @Override
    public Either<String, ClassroomGetDTO> createClassroom() {
        return classroomDao.createClassroom();
    }

    @Override
    public Either<String, ClassroomGetDTO> editClassroom() {
        return classroomDao.editClassroom();
    }

    @Override
    public Either<String, List<ClassroomGetDTO>> getAllClassrooms(String userId) {
        return classroomDao.getAllClassrooms(userId);
    }

    @Override
    public Either<String, ClassroomGetDTO> getClassroomById(String classroomId) {
        return classroomDao.getClassroomById(classroomId);
    }

    @Override
    public Either<String, ClassroomGetDTO> deleteClassroom(String classroomId) {
        return classroomDao.deleteClassroom(classroomId);
    }
}
