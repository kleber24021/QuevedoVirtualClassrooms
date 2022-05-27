package com.quevedo.virtualclassroomsserver.implementations.dao;

import com.quevedo.virtualclassroomsserver.common.config.QueriesLoader;
import com.quevedo.virtualclassroomsserver.common.models.dto.classroom.ClassroomGetDTO;
import com.quevedo.virtualclassroomsserver.common.models.dto.classroom.ClassroomPostDTO;
import com.quevedo.virtualclassroomsserver.common.models.dto.classroom.ClassroomPutDTO;
import com.quevedo.virtualclassroomsserver.common.models.server.classroom.Classroom;
import com.quevedo.virtualclassroomsserver.facade.dao.ClassroomDao;
import com.quevedo.virtualclassroomsserver.implementations.dao.utils.rowmappers.ClassroomRowMapper;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import com.quevedo.virtualclassroomsserver.implementations.dao.utils.ListComparator;

import javax.sql.DataSource;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class ClassroomDaoImpl implements ClassroomDao {

    private final JdbcTemplate jdbcTemplate;
    private final QueriesLoader queriesLoader;
    private final ClassroomRowMapper classroomRowMapper;
    private final DataSource connPool;

    @Inject
    public ClassroomDaoImpl(JdbcTemplate jdbcTemplate,
                            QueriesLoader queriesLoader,
                            ClassroomRowMapper classroomRowMapper,
                            DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.queriesLoader = queriesLoader;
        this.classroomRowMapper = classroomRowMapper;
        this.connPool = dataSource;

    }

    @Override
    public Either<String, ClassroomGetDTO> createClassroom(ClassroomPostDTO toCreate) {
        Either<String, ClassroomGetDTO> result;
        UUID generateUUID = UUID.randomUUID();
        TransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(connPool);
        TransactionStatus status = transactionManager.getTransaction(transactionDefinition);
        try {
            JdbcTemplate transactionTemplate = new JdbcTemplate(transactionManager.getDataSource());
            transactionTemplate.update(
                    queriesLoader.getInsertClassroom(),
                    generateUUID.toString(),
                    toCreate.getName(),
                    toCreate.getCourse(),
                    toCreate.getAdminUsername()
            );
            toCreate.getStudentsUsername().forEach(student ->
                    transactionTemplate.update(
                            queriesLoader.getInsertStudentClassroomRelation(),
                            generateUUID.toString(), student));
            transactionManager.commit(status);
            result = Either.right(ClassroomGetDTO.builder()
                    .uuidClassroom(generateUUID.toString())
                    .adminUsername(toCreate.getAdminUsername())
                    .name(toCreate.getName())
                    .course(toCreate.getCourse())
                    .studentsUsernames(toCreate.getStudentsUsername())
                    .build());
        } catch (Exception e) {
            transactionManager.rollback(status);
            result = Either.left("ERROR: " + e);
        }
        return result;
    }

    @Override
    //TODO check model to edit
    public Either<String, ClassroomGetDTO> editClassroom(ClassroomPutDTO toEdit) {
        try {
            jdbcTemplate.queryForObject(queriesLoader.getSelectClassroomById(), classroomRowMapper, toEdit.getUuidClassroom());
        } catch (IncorrectResultSizeDataAccessException incorrectSize) {
            return Either.left("The classroom to edit does not exist");
        }

        Either<String, ClassroomGetDTO> result;
        ListComparator<String> listComparator = new ListComparator<>();
        List<String> actualStudents = jdbcTemplate.query(queriesLoader.getSelectUsernamesFromClassroom(), usernameRowMapper(), toEdit.getUuidClassroom());
        List<String> newStudents = listComparator.findDifferencesBetweenLists(toEdit.getStudentsUsername(), actualStudents);
        List<String> toDeleteStudents = listComparator.findDifferencesBetweenLists(actualStudents, toEdit.getStudentsUsername());

        TransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(connPool);
        TransactionStatus status = transactionManager.getTransaction(transactionDefinition);
        try {
            JdbcTemplate transactionTemplate = new JdbcTemplate(transactionManager.getDataSource());
            transactionTemplate.update(
                    queriesLoader.getUpdateClassroom(),
                    toEdit.getName(),
                    toEdit.getCourse(),
                    toEdit.getAdminUsername(),
                    toEdit.getUuidClassroom()
            );
            newStudents.forEach(student ->
                    transactionTemplate.update(
                            queriesLoader.getInsertStudentClassroomRelation(),
                            toEdit.getUuidClassroom(),
                            student
                    ));
            toDeleteStudents.forEach(student ->
                    transactionTemplate.update(
                            queriesLoader.getDeleteStudentClassroomRelation(),
                            toEdit.getUuidClassroom(),
                            student
                    ));
            transactionManager.commit(status);
            result = Either.right(ClassroomGetDTO.builder()
                    .uuidClassroom(toEdit.getUuidClassroom())
                    .name(toEdit.getName())
                    .adminUsername(toEdit.getAdminUsername())
                    .course(toEdit.getCourse())
                    .studentsUsernames(toEdit.getStudentsUsername())
                    .build());
        } catch (Exception e) {
            transactionManager.rollback(status);
            result = Either.left("ERROR: " + e);
        }
        return result;
    }

    @Override
    public Either<String, List<ClassroomGetDTO>> getAllClassrooms(String userId) {
        Either<String, List<ClassroomGetDTO>> result;
        try {
            result = Either.right(jdbcTemplate.query(queriesLoader.getGetAllClassroomByUserId(), classroomRowMapper, userId).stream().map(Classroom::toGetDTO).collect(Collectors.toList()));
        } catch (DataAccessException dataAccessException) {
            result = Either.left("DB ERROR: " + dataAccessException.getMessage());
        }
        return result;
    }

    @Override
    public Either<String, ClassroomGetDTO> getClassroomById(String classroomId) {
        Either<String, ClassroomGetDTO> result;
        try {
            result = Either.right(jdbcTemplate.queryForObject(queriesLoader.getSelectClassroomById(), classroomRowMapper, classroomId).toGetDTO());
        } catch (DataAccessException dataAccessException) {
            result = Either.left("DB ERROR: " + dataAccessException.getMessage());
        }
        return result;
    }

    @Override
    public Either<String, Integer> deleteClassroom(String classroomId) {
        Either<String, Integer> result;
        try {
            result = Either.right(jdbcTemplate.update(queriesLoader.getDeleteClassroom(), classroomId));
        } catch (DataAccessException dataAccessException) {
            result = Either.left("DB ERROR: " + dataAccessException.getMessage());
        }
        return result;
    }

    private RowMapper<String> usernameRowMapper() {
        return (rs, rowNum) -> rs.getString("USERNAME");
    }
}
