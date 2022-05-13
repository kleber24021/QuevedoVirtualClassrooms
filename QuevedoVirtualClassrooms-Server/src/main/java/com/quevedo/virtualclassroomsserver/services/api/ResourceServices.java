package com.quevedo.virtualclassroomsserver.services.api;

import com.quevedo.virtualclassroomsserver.common.models.dto.resource.ResourceDetailGetDTO;
import com.quevedo.virtualclassroomsserver.common.models.dto.resource.ResourceLiteGetDTO;
import com.quevedo.virtualclassroomsserver.common.models.dto.resource.ResourcePostDTO;
import io.vavr.control.Either;

import java.io.File;
import java.util.List;

public interface ResourceServices {
    Either<String, ResourceLiteGetDTO> uploadResource(ResourcePostDTO toUpload, String baseUrl);
    Either<String, ResourceLiteGetDTO> editResource(ResourcePostDTO toEdit);
    Either<String, List<ResourceLiteGetDTO>> getAllResourcesOfClassroom(String classroomId);
    Either<String, ResourceDetailGetDTO> getDetailResource(String resourceUUID);
    Either<String, File> getResourceFile(String fileName);
    Either<String, Boolean> deleteResource(String resourceUUID);

}
