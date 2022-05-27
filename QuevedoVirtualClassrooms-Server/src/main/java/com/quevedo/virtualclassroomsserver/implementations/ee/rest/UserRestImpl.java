package com.quevedo.virtualclassroomsserver.implementations.ee.rest;

import com.quevedo.virtualclassroomsserver.common.constants.RestConsts;
import com.quevedo.virtualclassroomsserver.common.models.dto.user.UserPostPutDTO;
import com.quevedo.virtualclassroomsserver.facade.ee.rest.UserRest;
import com.quevedo.virtualclassroomsserver.facade.services.UserServices;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

import java.util.concurrent.atomic.AtomicReference;

@Path("/users")
public class UserRestImpl implements UserRest {
    private final UserServices userServices;

    @Inject
    public UserRestImpl(UserServices userServices) {
        this.userServices = userServices;
    }

    @Override
    @RolesAllowed({RestConsts.ROLE_TEACHER})
    public Response createUser(UserPostPutDTO userPostPut) {
        AtomicReference<Response> response = new AtomicReference<>();
        userServices.createUser(userPostPut)
                .peek(user -> response.set(Response.ok(user).build()))
                .peekLeft(error -> response.set(Response.status(Response.Status.BAD_REQUEST).entity(error).build()));
        return response.get();
    }

    @Override
    @RolesAllowed({RestConsts.ROLE_TEACHER})
    public Response editUser(UserPostPutDTO userPostPutDTO) {
        AtomicReference<Response> response = new AtomicReference<>();
        userServices.editUser(userPostPutDTO)
                .peek(user -> response.set(Response.ok(user).build()))
                .peekLeft(error -> response.set(Response.status(Response.Status.BAD_REQUEST).entity(error).build()));
        return response.get();
    }

    @Override
    @RolesAllowed({RestConsts.ROLE_TEACHER, RestConsts.ROLE_STUDENT})
    public Response getAllUsers() {
        AtomicReference<Response> response = new AtomicReference<>();
        userServices.getAllUsers()
                .peek(users -> response.set(Response.ok(users).build()))
                .peekLeft(error -> response.set(Response.status(Response.Status.BAD_REQUEST).entity(error).build()));
        return response.get();
    }

    @Override
    @RolesAllowed({RestConsts.ROLE_TEACHER, RestConsts.ROLE_STUDENT})
    public Response getAllUsersByClassroomId(String classroomId) {
        AtomicReference<Response> response = new AtomicReference<>();
        userServices.getAllUsuariosByClassroom(classroomId)
                .peek(users -> response.set(Response.ok(users).build()))
                .peekLeft(error -> response.set(Response.status(Response.Status.BAD_REQUEST).entity(error).build()));
        return response.get();
    }

    @Override
    @RolesAllowed({RestConsts.ROLE_TEACHER, RestConsts.ROLE_STUDENT})
    public Response getUser(String userId) {
        AtomicReference<Response> response = new AtomicReference<>();
        userServices.getUser(userId)
                .peek(user -> response.set(Response.ok(user).build()))
                .peekLeft(error -> response.set(Response.status(Response.Status.BAD_REQUEST).entity(error).build()));
        return response.get();
    }

    @Override
    @RolesAllowed({RestConsts.ROLE_TEACHER})
    public Response deleteUser(String userId) {
        AtomicReference<Response> response = new AtomicReference<>();
        userServices.deleteUser(userId)
                .peek(message -> response.set(Response.ok(message).build()))
                .peekLeft(error -> response.set(Response.status(Response.Status.BAD_REQUEST).entity(error).build()));
        return response.get();
    }
}
