package com.quevedo.virtualclassroomsserver.common.models.server.classroom;

import com.quevedo.virtualclassroomsserver.common.models.dto.classroom.ClassroomGetDTO;
import com.quevedo.virtualclassroomsserver.common.models.server.user.User;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
@Builder
public class Classroom {
    private UUID uuidClassroom;
    private String name;
    private String course;
    private User admin;
    private List<User> students;

    public ClassroomGetDTO toGetDTO(){
         ClassroomGetDTO toReturn = ClassroomGetDTO
                .builder()
                .uuidClassroom(uuidClassroom.toString())
                .name(name)
                .course(course)
                .adminUsername(admin.getUsername())
                .build();
         if (students != null && !students.isEmpty()){
             toReturn.setStudentsUsernames(students.stream().map(User::getUsername).collect(Collectors.toList()));
         }
         return toReturn;
    }
}
