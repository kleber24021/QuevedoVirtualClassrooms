package com.quevedo.virtualclassroomsserver.common.models.dto.user;

import com.quevedo.virtualclassroomsserver.common.models.common.UserType;

public class UserPostPutDTO {
    private String username;
    private String password;
    private String name;
    private String surname;
    private String email;
    private String profileImage;
    private UserType userType;
}
