package com.quevedo.virtualclassroomsserver.dao.implementation;

import com.quevedo.virtualclassroomsserver.common.config.QueriesLoader;
import com.quevedo.virtualclassroomsserver.common.models.dto.classroom.ClassroomGetDTO;
import com.quevedo.virtualclassroomsserver.common.models.server.classroom.Classroom;
import com.quevedo.virtualclassroomsserver.dao.api.ClassroomDao;
import com.quevedo.virtualclassroomsserver.dao.utils.rowmappers.ClassroomRowMapper;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.stream.Collectors;

public class ClassroomDaoImpl implements ClassroomDao {

    private final JdbcTemplate jdbcTemplate;
    private final QueriesLoader queriesLoader;
    private final ClassroomRowMapper classroomRowMapper;

    @Inject
    public ClassroomDaoImpl(JdbcTemplate jdbcTemplate, QueriesLoader queriesLoader, ClassroomRowMapper classroomRowMapper){
        this.jdbcTemplate = jdbcTemplate;
        this.queriesLoader = queriesLoader;
        this.classroomRowMapper = classroomRowMapper;
    }

    //TODO: add select of students to all calls
    @Override
    public Either<String, ClassroomGetDTO> createClassroom() {
        return null;
    }

    @Override
    public Either<String, ClassroomGetDTO> editClassroom() {
        return null;
    }

    @Override
    public Either<String, List<ClassroomGetDTO>> getAllClassrooms(String userId) {
        Either<String, List<ClassroomGetDTO>> result;
        try {
            result = Either.right(jdbcTemplate.query(queriesLoader.getGetAllClassroomByUserId(), classroomRowMapper, userId).stream().map(Classroom::toGetDTO).collect(Collectors.toList()));
        }catch (DataAccessException dataAccessException){
            result = Either.left("DB ERROR: " + dataAccessException.getMessage());
        }
        return result;
    }

    @Override
    public Either<String, ClassroomGetDTO> getClassroomById(String classroomId) {
        return null;
    }

    @Override
    public Either<String, ClassroomGetDTO> deleteClassroom(String classroomId) {
        return null;
    }
}
