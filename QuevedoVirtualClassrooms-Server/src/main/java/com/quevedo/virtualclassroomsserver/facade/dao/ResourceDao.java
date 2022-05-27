package com.quevedo.virtualclassroomsserver.facade.dao;

import com.quevedo.virtualclassroomsserver.common.models.common.ResourceType;

import com.quevedo.virtualclassroomsserver.common.models.dto.resource.ResourceDetailGetDTO;
import com.quevedo.virtualclassroomsserver.common.models.dto.resource.ResourceLiteGetDTO;
import com.quevedo.virtualclassroomsserver.common.models.dto.resource.ResourcePostDTO;
import com.quevedo.virtualclassroomsserver.common.models.server.resource.Resource;
import io.vavr.control.Either;

import java.io.File;
import java.io.InputStream;
import java.util.List;

public interface ResourceDao {
    Either<String, ResourceLiteGetDTO> uploadResource(Resource toUpload, InputStream toSaveInDisk, String fileExtension);

    Either<String, Integer> editResource(ResourcePostDTO toEdit);

    Either<String, List<ResourceLiteGetDTO>> getAllResourcesOfClassroom(String classroomId, ResourceType resourceType);

    Either<String, ResourceDetailGetDTO> getDetailResource(String resourceUUID);

    Either<String, File> getResourceFile(String resourceUUID);

    String deleteResource(String resourceUUID);
}
