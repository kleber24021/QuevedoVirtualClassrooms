package com.quevedo.virtualclassroomsserver.facade.services;

import com.quevedo.virtualclassroomsserver.common.models.common.UserType;
import com.quevedo.virtualclassroomsserver.common.models.dto.user.UserGetDTO;
import com.quevedo.virtualclassroomsserver.common.models.dto.user.UserPostPutDTO;
import io.vavr.control.Either;

import java.util.List;

public interface UserServices {
    Either<String, UserGetDTO> createUser(UserPostPutDTO userPostPutDTO);
    Either<String, UserGetDTO> editUser(UserPostPutDTO userPostPutDTO);
    Either<String, List<UserGetDTO>> getAllUsers();
    Either<String, List<UserGetDTO>> getAllUsuariosByClassroom(String classroomId);
    Either<String, UserGetDTO> getUser(String userId);
    Either<String, String> deleteUser(String userId);
    Either<String, UserType> checkLogin(String username, String pass);
}
