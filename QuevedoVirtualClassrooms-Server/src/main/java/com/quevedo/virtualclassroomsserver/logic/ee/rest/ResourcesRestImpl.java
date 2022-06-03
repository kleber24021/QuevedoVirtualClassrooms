package com.quevedo.virtualclassroomsserver.logic.ee.rest;

import com.quevedo.virtualclassroomsserver.common.constants.RestConsts;
import com.quevedo.virtualclassroomsserver.common.models.common.ResourceType;
import com.quevedo.virtualclassroomsserver.common.models.dto.resource.ResourcePostDTO;
import com.quevedo.virtualclassroomsserver.facade.ee.rest.ResourcesRest;
import com.quevedo.virtualclassroomsserver.facade.services.ResourceServices;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

import java.util.concurrent.atomic.AtomicReference;


@Path("/resources")
public class ResourcesRestImpl implements ResourcesRest {
    private final ResourceServices resourcesServices;
    @Inject
    public ResourcesRestImpl(ResourceServices resourcesServices) {
        this.resourcesServices = resourcesServices;
    }


    @Override
    @RolesAllowed({RestConsts.ROLE_TEACHER, RestConsts.ROLE_STUDENT})
    public Response getAllResourcesOfClassroom(String classroomId, String resourceType) {
        AtomicReference<Response> response = new AtomicReference<>();
        resourcesServices.getAllResourcesOfClassroom(classroomId, ResourceType.getTypeByString(resourceType))
                .peek(resources -> response.set(Response.ok(resources).build()))
                .peekLeft(error -> response.set(Response.status(Response.Status.BAD_REQUEST).entity(error).build()));
        return response.get();
    }

    @Override
    @RolesAllowed({RestConsts.ROLE_TEACHER, RestConsts.ROLE_STUDENT})
    public Response getResourceDetail(String resourceId) {
        AtomicReference<Response> response = new AtomicReference<>();
        resourcesServices.getDetailResource(resourceId)
                .peek(resources -> response.set(Response.ok(resources).build()))
                .peekLeft(error -> response.set(Response.status(Response.Status.BAD_REQUEST).entity(error).build()));
        return response.get();
    }

    @Override
    @RolesAllowed({RestConsts.ROLE_TEACHER})
    public Response editResourceInfo(ResourcePostDTO toEdit) {
        AtomicReference<Response> response = new AtomicReference<>();
        resourcesServices.editResource(toEdit)
                .peek(resources -> response.set(Response.accepted(resources).build()))
                .peekLeft(error -> response.set(Response.status(Response.Status.BAD_REQUEST).entity(error).build()));
        return response.get();
    }

    @Override
    @RolesAllowed({RestConsts.ROLE_TEACHER})
    public Response deleteResource(String resourceId) {
         return Response.ok(resourcesServices.deleteResource(resourceId)).build();
    }


}