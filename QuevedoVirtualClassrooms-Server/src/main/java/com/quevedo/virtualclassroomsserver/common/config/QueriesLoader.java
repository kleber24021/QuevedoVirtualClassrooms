package com.quevedo.virtualclassroomsserver.common.config;

import jakarta.inject.Singleton;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.Map;

@Log4j2
@Getter
@Singleton
public class QueriesLoader {
    public static final String INSERT_RESOURCE = "INSERT_RESOURCE";
    public static final String EDIT_RESOURCE = "EDIT_RESOURCE";
    public static final String GET_ALL_RESOURCES_BY_CLASSROOM = "GET_ALL_RESOURCES_BY_CLASSROOM";
    public static final String GET_RESOURCE_BY_ID = "GET_RESOURCE_BY_ID";
    public static final String DELETE_RESOURCE_BY_ID = "DELETE_RESOURCE_BY_ID";
    public static final String INSERT_CLASSROOM = "INSERT_CLASSROOM";
    public static final String EDIT_CLASSROOM = "EDIT_CLASSROOM";
    public static final String GET_ALL_CLASSROOM_BY_USER_ID = "GET_ALL_CLASSROOM_BY_USER_ID";
    public static final String DELETE_CLASSROOM_BY_ID = "DELETE_CLASSROOM_BY_ID";
    public static final String GET_ALL_COMMENTS_BY_RESOURCE = "GET_ALL_COMMENTS_BY_RESOURCE";
    public static final String INSERT_STUDENT_CLASSROOM = "INSERT_STUDENT_CLASSROOM";
    public static final String DELETE_STUDENT_CLASSROOM = "DELETE_STUDENT_CLASSROOM";
    public static final String SELECT_CLASSROOM_BY_ID = "SELECT_CLASSROOM_BY_ID";
    public static final String SELECT_STUDENTS_BY_CLASSROOM_ID = "SELECT_STUDENTS_BY_CLASSROOM_ID";
    public static final String INSERT_USER = "INSERT_USER";
    public static final String UPDATE_USER = "UPDATE_USER";
    public static final String GET_ALL_USERS = "GET_ALL_USERS";
    public static final String GET_ALL_STUDENTS_BY_CLASSROOM_ID = "GET_ALL_STUDENTS_BY_CLASSROOM_ID";
    public static final String GET_TEACHER_BY_CLASSROOM_ID = "GET_TEACHER_BY_CLASSROOM_ID";
    public static final String GET_USER = "GET_USER";
    public static final String DELETE_USER = "DELETE_USER";

    private String insertResource;
    private String updateResource;
    private String getAllResourcesByClassroomId;
    private String getResourceById;
    private String deleteResourceById;

    private String insertClassroom;
    private String updateClassroom;
    private String getAllClassroomByUserId;
    private String deleteClassroom;
    private String selectClassroomById;

    private String getAllCommentsByResource;

    private String insertStudentClassroomRelation;
    private String selectUsernamesFromClassroom;
    private String deleteStudentClassroomRelation;

    private String insertUser;
    private String updateUser;
    private String getAllUsers;
    private String getAllStudentsByClassroomId;
    private String getTeacherByClassroomId;
    private String getUser;
    private String deleteUser;

    void loadQueries(InputStream fileStream){
        try {
            Yaml yaml = new Yaml();
            Map<String, String> map;
            map = (Map<String, String>) yaml.loadAll(fileStream).iterator().next();
            this.insertResource = map.get(INSERT_RESOURCE);
            this.updateResource = map.get(EDIT_RESOURCE);
            this.getAllResourcesByClassroomId = map.get(GET_ALL_RESOURCES_BY_CLASSROOM);
            this.getResourceById = map.get(GET_RESOURCE_BY_ID);
            this.deleteResourceById = map.get(DELETE_RESOURCE_BY_ID);
            this.insertClassroom = map.get(INSERT_CLASSROOM);
            this.updateClassroom = map.get(EDIT_CLASSROOM);
            this.getAllClassroomByUserId = map.get(GET_ALL_CLASSROOM_BY_USER_ID);
            this.deleteClassroom = map.get(DELETE_CLASSROOM_BY_ID);
            this.getAllCommentsByResource = map.get(GET_ALL_COMMENTS_BY_RESOURCE);
            this.insertStudentClassroomRelation = map.get(INSERT_STUDENT_CLASSROOM);
            this.deleteStudentClassroomRelation = map.get(DELETE_STUDENT_CLASSROOM);
            this.selectClassroomById = map.get(SELECT_CLASSROOM_BY_ID);
            this.selectUsernamesFromClassroom = map.get(SELECT_STUDENTS_BY_CLASSROOM_ID);
            this.insertUser = map.get(INSERT_USER);
            this.updateUser = map.get(UPDATE_USER);
            this.getAllUsers = map.get(GET_ALL_USERS);
            this.getAllStudentsByClassroomId = map.get(GET_ALL_STUDENTS_BY_CLASSROOM_ID);
            this.getTeacherByClassroomId = map.get(GET_TEACHER_BY_CLASSROOM_ID);
            this.getUser = map.get(GET_USER);
            this.deleteUser = map.get(DELETE_USER);

        }catch (Exception exception){
            log.error(exception.getMessage(), exception);
        }
    }
}
