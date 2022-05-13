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
    private String insertResource;
    private String updateResource;
    private String getAllResourcesByClassroomId;
    private String getResourceById;
    private String deleteResourceById;

    private String insertClassroom;
    private String updateClassroom;
    private String getAllClassroomByUserId;
    private String deleteClassroom;

    void loadQueries(InputStream fileStream){
        try {
            Yaml yaml = new Yaml();
            Map<String, String> map;
            map = (Map<String, String>) yaml.loadAll(fileStream).iterator().next();
            this.insertResource = map.get("INSERT_RESOURCE");
            this.updateResource = map.get("EDIT_RESOURCE");
            this.getAllResourcesByClassroomId = map.get("GET_ALL_RESOURCES_BY_CLASSROOM");
            this.getResourceById = map.get("GET_RESOURCE_BY_ID");
            this.deleteResourceById = map.get("DELETE_RESOURCE_BY_ID");
            this.insertClassroom = map.get("INSERT_CLASSROOM");
            this.updateClassroom = map.get("EDIT_CLASSROOM");
            this.getAllClassroomByUserId = map.get("GET_ALL_CLASSROOM_BY_USER_ID");
            this.deleteClassroom = map.get("DELETE_CLASSROOM_BY_ID");

        }catch (Exception exception){
            log.error(exception.getMessage(), exception);
        }
    }
}
