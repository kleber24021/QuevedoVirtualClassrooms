package com.quevedo.virtualclassroomsserver.implementations.ee.rest;

import com.quevedo.virtualclassroomsserver.common.constants.RestConsts;
import com.quevedo.virtualclassroomsserver.common.models.dto.classroom.ClassroomPostDTO;
import com.quevedo.virtualclassroomsserver.common.models.dto.classroom.ClassroomPutDTO;
import com.quevedo.virtualclassroomsserver.facade.ee.rest.ClassroomRest;
import com.quevedo.virtualclassroomsserver.facade.services.ClassroomServices;
import jakarta.annotation.security.RolesAllowed;
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
    @RolesAllowed({RestConsts.ROLE_TEACHER, RestConsts.ROLE_STUDENT})
    public Response getAllClassroomsByUserId(String userId) {
        AtomicReference<Response> response = new AtomicReference<>();
        classroomServices.getAllClassroomsByUserId(userId)
                .peek(classroomGetDTOS -> response.set(Response.ok(classroomGetDTOS).build()))
                .peekLeft(error -> response.set(Response.status(Response.Status.BAD_REQUEST).entity(error).build()));
        return response.get();
    }

    @Override
    @RolesAllowed({RestConsts.ROLE_TEACHER})
    public Response createClassroom(ClassroomPostDTO toCreate) {
        AtomicReference<Response> response = new AtomicReference<>();
        classroomServices.createClassroom(toCreate)
                .peek(classroomGetDTO -> response.set(Response.accepted(classroomGetDTO).build()))
                .peekLeft(error -> response.set(Response.status(Response.Status.BAD_REQUEST).entity(error).build()));
                return response.get();
    }

    @Override
    @RolesAllowed({RestConsts.ROLE_TEACHER, RestConsts.ROLE_STUDENT})
    public Response getClassroomById(String classroomId) {
        AtomicReference<Response> response = new AtomicReference<>();
        classroomServices.getClassroomById(classroomId)
                .peek(classroomGetDTO -> response.set(Response.accepted(classroomGetDTO).build()))
                .peekLeft(error -> response.set(Response.status(Response.Status.BAD_REQUEST).entity(error).build()));
        return response.get();
    }

    @Override
    @RolesAllowed({RestConsts.ROLE_TEACHER})
    public Response editClassroom(ClassroomPutDTO toEdit) {
        AtomicReference<Response> response = new AtomicReference<>();
        classroomServices.editClassroom(toEdit)
                .peek(classroomGetDTO -> response.set(Response.accepted(classroomGetDTO).build()))
                .peekLeft(error -> response.set(Response.status(Response.Status.BAD_REQUEST).entity(error).build()));
        return response.get();
    }

    @Override
    @RolesAllowed({RestConsts.ROLE_TEACHER})
    public Response deleteClassroom(String classroomId) {
        AtomicReference<Response> response = new AtomicReference<>();
        classroomServices.deleteClassroom(classroomId)
                .peek(message -> response.set(Response.status(Response.Status.NO_CONTENT).entity(message).build()))
                .peekLeft(error -> response.set(Response.status(Response.Status.BAD_REQUEST).entity(error).build()));
        return response.get();
    }

}
