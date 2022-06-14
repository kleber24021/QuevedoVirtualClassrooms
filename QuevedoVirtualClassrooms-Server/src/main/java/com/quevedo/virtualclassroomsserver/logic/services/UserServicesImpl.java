package com.quevedo.virtualclassroomsserver.logic.services;

import com.quevedo.virtualclassroomsserver.common.models.common.UserType;
import com.quevedo.virtualclassroomsserver.common.models.dto.user.UserGetDTO;
import com.quevedo.virtualclassroomsserver.common.models.dto.user.UserPostPutDTO;
import com.quevedo.virtualclassroomsserver.common.models.server.user.User;
import com.quevedo.virtualclassroomsserver.facade.dao.UserDao;
import com.quevedo.virtualclassroomsserver.facade.services.ServiceHashPassword;
import com.quevedo.virtualclassroomsserver.facade.services.UserServices;
import io.vavr.control.Either;
import jakarta.inject.Inject;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class UserServicesImpl implements UserServices {
    private final UserDao userDao;
    private final ServiceHashPassword serviceHashPassword;

    @Inject
    public UserServicesImpl(UserDao userDao, ServiceHashPassword serviceHashPassword){
        this.userDao = userDao;
        this.serviceHashPassword = serviceHashPassword;
    }

    @Override
    public Either<String, UserGetDTO> createUser(UserPostPutDTO userPostPutDTO) {
        String hashedPassword = serviceHashPassword.hash(userPostPutDTO.getPassword());
        userPostPutDTO.setPassword(hashedPassword);
        return userDao.createUser(userPostPutDTO);
    }

    @Override
    public Either<String, UserGetDTO> editUser(UserPostPutDTO userPostPutDTO) {
        AtomicReference<Either<String, UserGetDTO>> resultReference = new AtomicReference<>();
        if (userPostPutDTO.getPassword() != null && !userPostPutDTO.getPassword().isBlank()){
            resultReference.set(userDao.editUser(userPostPutDTO));
            if (resultReference.get().isRight()){
                userDao.editUserPassword(userPostPutDTO.getUsername(), serviceHashPassword.hash(userPostPutDTO.getPassword()))
                        .peekLeft(errorMsg -> resultReference.set(Either.left("Se ha cambiado la información pero no la contraseña: " + errorMsg)));
            }
        }else {
            resultReference.set(userDao.editUser(userPostPutDTO));
        }
        return resultReference.get();
    }

    @Override
    public Either<String, List<UserGetDTO>> getAllUsers() {
        return userDao.getAllUsers();
    }

    @Override
    public Either<String, List<UserGetDTO>> getAllUsuariosByClassroom(String classroomId) {
        return userDao.getUsuariosByClassroom(classroomId);
    }

    @Override
    public Either<String, UserGetDTO> getUser(String userId) {
        return userDao.getUser(userId);
    }

    @Override
    public Either<String, String> deleteUser(String userId) {
        return userDao.deleteUser(userId);
    }

    @Override
    public Either<String, UserType> checkLogin(String username, String pass) {
        Either<String, UserType> result;
        Either<String, User> queryResult = userDao.getUserDetailForLogin(username);

        if (queryResult.isRight()){
            if (queryResult.get() != null && serviceHashPassword.verify(queryResult.get().getHashedPassword(), pass)){
                result = Either.right(queryResult.get().getUserType());
            }else {
                result = Either.right(null);
            }
        }else {
            result = Either.left(queryResult.getLeft());
        }
        return result;
    }
}
