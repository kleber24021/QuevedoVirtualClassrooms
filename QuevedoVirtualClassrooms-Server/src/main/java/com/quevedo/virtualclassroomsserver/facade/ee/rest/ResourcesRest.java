package com.quevedo.virtualclassroomsserver.facade.ee.rest;

import com.quevedo.virtualclassroomsserver.common.models.dto.resource.ResourcePostDTO;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;


@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface ResourcesRest {

    @Path("/all")
    @GET
    Response getAllResourcesOfClassroom(@QueryParam("classroomId") String classroomId, @QueryParam("resourceType") String resourceType);

    @Path("/detail/{resourceId}")
    @GET
    Response getResourceDetail(@PathParam("resourceId") String resourceId);

    @PUT
    Response editResourceInfo(ResourcePostDTO toEdit);

    @DELETE
    @Path("/{resourceId}")
    Response deleteResource(@PathParam("resourceId") String resourceId);
}
