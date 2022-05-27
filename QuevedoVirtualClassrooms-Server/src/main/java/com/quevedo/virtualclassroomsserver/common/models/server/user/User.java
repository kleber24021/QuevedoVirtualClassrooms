package com.quevedo.virtualclassroomsserver.common.models.server.user;

import com.quevedo.virtualclassroomsserver.common.models.common.UserType;
import com.quevedo.virtualclassroomsserver.common.models.dto.user.UserGetDTO;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User {
    private String username;
    private String hashedPassword;
    private String name;
    private String surname;
    private String email;
    private String profileImage;
    private UserType userType;

    public UserGetDTO toGetDTO(){
        return UserGetDTO.builder()
                .username(this.username)
                .name(this.name)
                .surname(this.surname)
                .email(this.email)
                .profileImage(this.profileImage)
                .userType(this.userType)
                .build();
    }
}
