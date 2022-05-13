package com.quevedo.virtualclassroomsserver.dao.api;

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

    Either<String, ResourceLiteGetDTO> editResource(ResourcePostDTO toEdit);

    Either<String, List<ResourceLiteGetDTO>> getAllResourcesOfClassroom(String classroomId);

    Either<String, ResourceDetailGetDTO> getDetailResource(String resourceUUID);

    Either<String, File> getResourceFile(String resourceUUID);

    Either<String, Boolean> deleteResource(String resourceUUID);
}
