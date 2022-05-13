package com.quevedo.virtualclassroomsserver.common.models.dto.classroom;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ClassroomGetDTO {
    private String uuidClassroom;
    private String name;
    private String course;
    private String adminUsername;
    private List<String> studentsUsernames;
}