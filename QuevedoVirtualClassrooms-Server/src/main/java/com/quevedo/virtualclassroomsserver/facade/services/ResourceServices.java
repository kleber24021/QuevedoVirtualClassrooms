package com.quevedo.virtualclassroomsserver.facade.services;

import com.quevedo.virtualclassroomsserver.common.models.dto.resource.ResourceDetailGetDTO;
import com.quevedo.virtualclassroomsserver.common.models.dto.resource.ResourceLiteGetDTO;
import com.quevedo.virtualclassroomsserver.common.models.dto.resource.ResourcePostDTO;
import com.quevedo.virtualclassroomsserver.common.models.dto.resource.ResourcePutDTO;
import com.quevedo.virtualclassroomsserver.common.models.dto.resource.comment.ResourceCommentPost;
import com.quevedo.virtualclassroomsserver.common.models.server.resource.ResourceComment;
import io.vavr.control.Either;

import java.io.File;
import java.util.List;

public interface ResourceServices {
    Either<String, ResourceLiteGetDTO> uploadResource(ResourcePostDTO toUpload);
    Either<String, String> editResource(ResourcePutDTO toEdit);
    Either<String, List<ResourceLiteGetDTO>> getAllResourcesOfClassroom(String classroomId, String resourceType);
    Either<String, ResourceDetailGetDTO> getDetailResource(String resourceUUID);
    Either<String, ResourceComment> insertComment(ResourceCommentPost resourceComment);
    Either<String, File> getResourceFile(String fileName, String username);
    String deleteResource(String resourceUUID);
}
