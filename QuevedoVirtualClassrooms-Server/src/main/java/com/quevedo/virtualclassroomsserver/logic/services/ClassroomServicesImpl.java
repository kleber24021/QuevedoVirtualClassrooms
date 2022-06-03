package com.quevedo.virtualclassroomsserver.logic.services;

import com.quevedo.virtualclassroomsserver.common.models.dto.classroom.ClassroomGetDTO;
import com.quevedo.virtualclassroomsserver.common.models.dto.classroom.ClassroomPostDTO;
import com.quevedo.virtualclassroomsserver.common.models.dto.classroom.ClassroomPutDTO;
import com.quevedo.virtualclassroomsserver.facade.dao.ClassroomDao;
import com.quevedo.virtualclassroomsserver.facade.services.ClassroomServices;
import io.vavr.control.Either;
import jakarta.inject.Inject;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class ClassroomServicesImpl implements ClassroomServices {

    private final ClassroomDao classroomDao;

    @Inject
    public ClassroomServicesImpl(ClassroomDao classroomDao) {
        this.classroomDao = classroomDao;
    }

    @Override
    public Either<String, ClassroomGetDTO> createClassroom(ClassroomPostDTO toCreate) {
        return classroomDao.createClassroom(toCreate);
    }

    @Override
    public Either<String, ClassroomGetDTO> editClassroom(ClassroomPutDTO toEdit) {
        return classroomDao.editClassroom(toEdit);
    }

    @Override
    public Either<String, List<ClassroomGetDTO>> getAllClassroomsByUserId(String userId) {
        return classroomDao.getAllClassrooms(userId);
    }

    @Override
    public Either<String, ClassroomGetDTO> getClassroomById(String classroomId) {
        return classroomDao.getClassroomById(classroomId);
    }

    @Override
    public Either<String, String> deleteClassroom(String classroomId) {
        AtomicReference<Either<String ,String>> result = new AtomicReference<>();
        classroomDao.deleteClassroom(classroomId)
                .peek(affectedRows -> {
                    if (affectedRows > 0){
                        result.set(Either.right("Classroom borrado correctamente"));
                    }else {
                        result.set(Either.left("No existe el classroom indicado"));
                    }
                })
                .peekLeft(errorMessage -> result.set(Either.left(errorMessage)));
        return result.get();
    }
}
