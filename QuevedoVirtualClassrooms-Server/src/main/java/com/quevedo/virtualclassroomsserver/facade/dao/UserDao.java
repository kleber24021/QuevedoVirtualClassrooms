package com.quevedo.virtualclassroomsserver.facade.dao;

import com.quevedo.virtualclassroomsserver.common.models.dto.user.UserGetDTO;
import com.quevedo.virtualclassroomsserver.common.models.dto.user.UserPostPutDTO;
import com.quevedo.virtualclassroomsserver.common.models.server.user.User;
import io.vavr.control.Either;

import java.util.List;

public interface UserDao {
    Either<String, UserGetDTO> createUser(UserPostPutDTO userPostPutDTO);
    Either<String, UserGetDTO> editUser(UserPostPutDTO userPostPutDTO);
    Either<String, List<UserGetDTO>> getAllUsers();
    Either<String, List<UserGetDTO>> getUsuariosByClassroom(String classroomId);
    Either<String, UserGetDTO> getUser(String username);
    Either<String, User> getUserDetailForLogin(String username);
    Either<String, String> deleteUser(String username);
}
