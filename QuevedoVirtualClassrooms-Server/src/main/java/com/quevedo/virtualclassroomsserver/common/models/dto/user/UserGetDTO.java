package com.quevedo.virtualclassroomsserver.common.models.dto.user;

import com.quevedo.virtualclassroomsserver.common.models.common.UserType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserGetDTO {
    private String username;
    private String name;
    private String surname;
    private String email;
    private String profileImage;
    private UserType userType;
}
