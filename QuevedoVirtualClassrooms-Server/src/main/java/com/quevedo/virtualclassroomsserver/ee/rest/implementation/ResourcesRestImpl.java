package com.quevedo.virtualclassroomsserver.ee.rest.implementation;

import com.quevedo.virtualclassroomsserver.ee.rest.api.ResourcesRest;
import com.quevedo.virtualclassroomsserver.services.api.ResourceServices;
import jakarta.inject.Inject;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

import java.util.concurrent.atomic.AtomicReference;


@Path("/resources")
public class ResourcesRestImpl implements ResourcesRest {
    private final ResourceServices videosServices;
    @Inject
    public ResourcesRestImpl(ResourceServices videosServices) {
        this.videosServices = videosServices;
    }


    @Override
    public Response getAllResourcesOfClassroom(String classroomId) {
        AtomicReference<Response> response = new AtomicReference<>();
        videosServices.getAllResourcesOfClassroom(classroomId)
                .peek(resources -> response.set(Response.ok(resources).build()))
                .peekLeft(error -> response.set(Response.status(Response.Status.BAD_REQUEST).entity(error).build()));
        return response.get();
    }

    @Override
    public Response getResourceDetail(String resourceId) {
        AtomicReference<Response> response = new AtomicReference<>();
        videosServices.getDetailResource(resourceId)
                .peek(resources -> response.set(Response.ok(resources).build()))
                .peekLeft(error -> response.set(Response.status(Response.Status.BAD_REQUEST).entity(error).build()));
        return response.get();
    }


}