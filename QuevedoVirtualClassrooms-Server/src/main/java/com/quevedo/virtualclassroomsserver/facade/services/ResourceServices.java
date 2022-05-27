package com.quevedo.virtualclassroomsserver.facade.services;

import com.quevedo.virtualclassroomsserver.common.models.common.ResourceType;

import com.quevedo.virtualclassroomsserver.common.models.dto.resource.ResourceDetailGetDTO;
import com.quevedo.virtualclassroomsserver.common.models.dto.resource.ResourceLiteGetDTO;
import com.quevedo.virtualclassroomsserver.common.models.dto.resource.ResourcePostDTO;
import io.vavr.control.Either;

import java.io.File;
import java.util.List;

public interface ResourceServices {
    Either<String, ResourceLiteGetDTO> uploadResource(ResourcePostDTO toUpload);
    Either<String, String> editResource(ResourcePostDTO toEdit);
    Either<String, List<ResourceLiteGetDTO>> getAllResourcesOfClassroom(String classroomId, ResourceType resourceType);
    Either<String, ResourceDetailGetDTO> getDetailResource(String resourceUUID);
    Either<String, File> getResourceFile(String fileName);
    String deleteResource(String resourceUUID);

}
