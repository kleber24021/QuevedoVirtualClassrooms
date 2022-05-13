package com.quevedo.virtualclassroomsserver.ee.rest.api;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;


@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface ResourcesRest {

    @Path("/all")
    @GET
    Response getAllResourcesOfClassroom(@QueryParam("classroomId") String classroomId);

    @Path("/detail/{resourceId}")
    @GET
    Response getResourceDetail(@PathParam("resourceId") String resourceId);
}
