package com.quevedo.virtualclassroomsserver.ee.rest.api;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;


@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface ClassroomRest {
    @GET
    Response getAllClassroomsByUserId(@QueryParam("username") String userId);
}
