package com.quevedo.virtualclassroomsserver.logic.dao;

import com.quevedo.virtualclassroomsserver.common.config.QueriesLoader;
import com.quevedo.virtualclassroomsserver.common.models.dto.user.UserGetDTO;
import com.quevedo.virtualclassroomsserver.common.models.dto.user.UserPostPutDTO;
import com.quevedo.virtualclassroomsserver.common.models.server.user.User;
import com.quevedo.virtualclassroomsserver.facade.dao.UserDao;
import com.quevedo.virtualclassroomsserver.logic.dao.utils.rowmappers.UserRowMapper;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Log4j2
public class UserDaoImpl implements UserDao {

    private final QueriesLoader queriesLoader;
    private final JdbcTemplate template;
    private final UserRowMapper userRowMapper;

    @Inject
    public UserDaoImpl(QueriesLoader queriesLoader,
                       JdbcTemplate template,
                       UserRowMapper userRowMapper) {
        this.queriesLoader = queriesLoader;
        this.template = template;
        this.userRowMapper = userRowMapper;
    }

    // TODO: 22/05/2022 Añadir comprobación de contraseñas, al actualizar o borrar

    @Override
    public Either<String, UserGetDTO> createUser(UserPostPutDTO userPostPutDTO) {
        Either<String, UserGetDTO> result;
        try {
            if (template.update(queriesLoader.getInsertUser(),
                    userPostPutDTO.getUsername(),
                    userPostPutDTO.getName(),
                    userPostPutDTO.getSurname(),
                    userPostPutDTO.getPassword(),
                    userPostPutDTO.getProfileImage(),
                    userPostPutDTO.getUserType().getVal()) > 0) {
                result = Either.right(
                        UserGetDTO.builder()
                                .username(userPostPutDTO.getUsername())
                                .name(userPostPutDTO.getName())
                                .surname(userPostPutDTO.getSurname())
                                .profileImage(userPostPutDTO.getProfileImage())
                                .userType(userPostPutDTO.getUserType())
                                .build()
                );
            } else {
                result = Either.left("Unexpected error: record could not be saved");
            }
        } catch (DataAccessException dataAccessException) {
            log.error(dataAccessException.getMessage(), dataAccessException);
            result = Either.left(dataAccessException.toString());
        }
        return result;
    }

    @Override
    public Either<String, UserGetDTO> editUser(UserPostPutDTO userPostPutDTO) {
        Either<String, UserGetDTO> result;
        try {
            if (template.update(queriesLoader.getUpdateUser(),
                    userPostPutDTO.getName(),
                    userPostPutDTO.getSurname(),
                    userPostPutDTO.getPassword(),
                    userPostPutDTO.getProfileImage(),
                    userPostPutDTO.getUserType().getVal(),
                    userPostPutDTO.getUsername()) > 0) {
                result = Either.right(UserGetDTO.builder()
                        .username(userPostPutDTO.getUsername())
                        .name(userPostPutDTO.getName())
                        .surname(userPostPutDTO.getSurname())
                        .profileImage(userPostPutDTO.getProfileImage())
                        .userType(userPostPutDTO.getUserType())
                        .build());
            } else {
                result = Either.left("ERROR: Record could not be edited. Check username");
            }
        } catch (DataAccessException dataAccessException) {
            log.error(dataAccessException.getMessage(), dataAccessException);
            result = Either.left(dataAccessException.toString());
        }
        return result;
    }

    @Override
    public Either<String, List<UserGetDTO>> getAllUsers() {
        Either<String, List<UserGetDTO>> result;
        try {
            List<User> users = template.query(queriesLoader.getGetAllUsers(), userRowMapper);
            result = Either.right(users.stream().map(User::toGetDTO).collect(Collectors.toList()));
        } catch (DataAccessException dataAccessException) {
            log.error(dataAccessException.getMessage(), dataAccessException);
            result = Either.left(dataAccessException.toString());
        }
        return result;
    }

    @Override
    public Either<String, List<UserGetDTO>> getUsuariosByClassroom(String classroomId) {
        Either<String, List<UserGetDTO>> result;
        try {
            List<User> users = template.query(queriesLoader.getGetAllStudentsByClassroomId(), userRowMapper, classroomId);
            users.add(template.queryForObject(queriesLoader.getGetTeacherByClassroomId(), userRowMapper, classroomId));
            result = Either.right(users.stream().map(User::toGetDTO).collect(Collectors.toList()));
        } catch (DataAccessException dataAccessException) {
            log.error(dataAccessException);
            result = Either.left(dataAccessException.toString());
        }
        return result;
    }

    @Override
    public Either<String, UserGetDTO> getUser(String username) {
        Either<String, UserGetDTO> result;
        try {
            User fetchedUser = Objects.requireNonNull(template.queryForObject(queriesLoader.getGetUser(), userRowMapper, username));
            result = Either.right(fetchedUser.toGetDTO());
        } catch (IncorrectResultSizeDataAccessException incorrectResultSizeDataAccessException) {
            result = Either.left("This users does not exist");
        } catch (DataAccessException dataAccessException) {
            log.error(dataAccessException.getMessage(), dataAccessException);
            result = Either.left(dataAccessException.toString());
        }
        return result;
    }

    @Override
    public Either<String, User> getUserDetailForLogin(String username) {
        Either<String, User> result;
        try {
            User fetchedUser = Objects.requireNonNull(template.queryForObject(queriesLoader.getGetUser(), userRowMapper, username));
            result = Either.right(fetchedUser);
        } catch (IncorrectResultSizeDataAccessException incorrectResultSizeDataAccessException) {
            result = Either.left("This users does not exist");
        } catch (DataAccessException dataAccessException) {
            log.error(dataAccessException.getMessage(), dataAccessException);
            result = Either.left(dataAccessException.toString());
        }
        return result;
    }

    @Override
    public Either<String, String> deleteUser(String userId) {
        Either<String, String> result;
        try {
            if (template.update(queriesLoader.getDeleteUser(), userId) > 0) {
                result = Either.right("User deleted");
            } else {
                result = Either.left("User not deleted. Incorrect userId?");
            }
        } catch (DataAccessException dataAccessException) {
            log.error(dataAccessException.getMessage(), dataAccessException);
            result = Either.left(dataAccessException.toString());
        }
        return result;
    }
}
