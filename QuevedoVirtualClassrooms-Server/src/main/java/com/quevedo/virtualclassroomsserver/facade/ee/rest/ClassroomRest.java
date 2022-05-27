package com.quevedo.virtualclassroomsserver.facade.ee.rest;

import com.quevedo.virtualclassroomsserver.common.models.dto.classroom.ClassroomPostDTO;
import com.quevedo.virtualclassroomsserver.common.models.dto.classroom.ClassroomPutDTO;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;


@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface ClassroomRest {
    @GET
    Response getAllClassroomsByUserId(@QueryParam("username") String userId);

    @POST
    Response createClassroom(ClassroomPostDTO toCreate);

    @GET
    @Path("/{classroomId}")
    Response getClassroomById(@PathParam("classroomId") String classroomId);

    @PUT
    Response editClassroom(ClassroomPutDTO toEdit);

    @DELETE
    @Path("/{classroomId}")
    Response deleteClassroom(@PathParam("classroomId") String classroomId);
}
