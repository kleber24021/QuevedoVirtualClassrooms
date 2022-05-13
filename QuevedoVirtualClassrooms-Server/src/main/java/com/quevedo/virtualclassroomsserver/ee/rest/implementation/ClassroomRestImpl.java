package com.quevedo.virtualclassroomsserver.ee.rest.implementation;

import com.quevedo.virtualclassroomsserver.ee.rest.api.ClassroomRest;
import com.quevedo.virtualclassroomsserver.services.api.ClassroomServices;
import jakarta.inject.Inject;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

import java.util.concurrent.atomic.AtomicReference;

@Path("/classrooms")
public class ClassroomRestImpl implements ClassroomRest {
    private final ClassroomServices classroomServices;

    @Inject
    public ClassroomRestImpl(ClassroomServices classroomServices) {
        this.classroomServices = classroomServices;
    }

    @Override
    public Response getAllClassroomsByUserId(String userId) {
        AtomicReference<Response> response = new AtomicReference<>();
        classroomServices.getAllClassrooms(userId)
                .peek(classroomGetDTOS -> response.set(Response.ok(classroomGetDTOS).build()))
                .peekLeft(error -> response.set(Response.status(Response.Status.BAD_REQUEST).entity(error).build()));
        return response.get();
    }
}
