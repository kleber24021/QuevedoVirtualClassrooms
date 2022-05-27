package com.quevedo.virtualclassroomsserver.common.models.dto.classroom;

import lombok.Data;

import java.util.List;

@Data
public class ClassroomPutDTO {
    private String uuidClassroom;
    private String name;
    private String course;
    private String adminUsername;
    private List<String> studentsUsername;
}
