package com.quevedo.virtualclassroomsserver.facade.ee.rest;

import com.quevedo.virtualclassroomsserver.common.models.dto.user.UserPostPutDTO;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface UserRest {

    @POST
    Response createUser(UserPostPutDTO userPostPut);

    @PUT
    Response editUser(UserPostPutDTO userPostPutDTO);

    @GET
    Response getAllUsers();

    @GET
    @Path("/classroom/{classroomId}")
    Response getAllUsersByClassroomId(@PathParam("classroomId") String classroomId);

    @GET
    @Path("/{userId}")
    Response getUser(@PathParam("userId") String userId);

    @DELETE
    @Path("/{userId}")
    Response deleteUser(@PathParam("userId") String userId);
}
