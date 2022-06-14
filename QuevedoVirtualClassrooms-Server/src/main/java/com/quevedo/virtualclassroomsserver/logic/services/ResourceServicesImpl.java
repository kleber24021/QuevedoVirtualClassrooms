package com.quevedo.virtualclassroomsserver.logic.services;

import com.quevedo.virtualclassroomsserver.common.models.common.ResourceType;
import com.quevedo.virtualclassroomsserver.common.models.dto.resource.ResourceDetailGetDTO;
import com.quevedo.virtualclassroomsserver.common.models.dto.resource.ResourceLiteGetDTO;
import com.quevedo.virtualclassroomsserver.common.models.dto.resource.ResourcePostDTO;
import com.quevedo.virtualclassroomsserver.common.models.dto.resource.ResourcePutDTO;
import com.quevedo.virtualclassroomsserver.common.models.dto.resource.comment.ResourceCommentPost;
import com.quevedo.virtualclassroomsserver.common.models.server.resource.Resource;
import com.quevedo.virtualclassroomsserver.common.models.server.resource.ResourceComment;
import com.quevedo.virtualclassroomsserver.facade.dao.ResourceDao;
import com.quevedo.virtualclassroomsserver.facade.services.ResourceServices;
import io.vavr.control.Either;
import jakarta.inject.Inject;

import java.io.File;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public class ResourceServicesImpl implements ResourceServices {
    private final ResourceDao resourceDao;

    @Inject
    public ResourceServicesImpl(ResourceDao resourceDao) {
        this.resourceDao = resourceDao;
    }

    @Override
    public Either<String, ResourceLiteGetDTO> uploadResource(ResourcePostDTO toUpload) {
        if (toUpload.getResourceType().equals(ResourceType.VIDEO)) {
            if (!toUpload.getFileExtension().equals(".mp4")) {
                return Either.left("File extension not admitted for this resource type");
            }
        } else if (toUpload.getResourceType().equals(ResourceType.IMAGE) && !List.of(".jpg", ".png", ".jpeg").contains(toUpload.getFileExtension())) {
            return Either.left("File extension not admitted for this resource type");
        }
        Resource createdResource = new Resource();
        //        0. Pasamos toda la información que se pueda del DTO al objeto propio
        createdResource.setResourceName(toUpload.getResourceName());
        createdResource.setResourceType(toUpload.getResourceType());
        createdResource.setTimestamp(LocalDateTime.now());
        createdResource.setClassroomUUID(UUID.fromString(toUpload.getClassroomUUID()));
        //        1. Generar UUID para el recurso
        createdResource.setUuidResource(UUID.randomUUID());
        return resourceDao.uploadResource(createdResource, toUpload.getFileStream(), toUpload.getFileExtension());
    }

    @Override
    public Either<String, String> editResource(ResourcePutDTO toEdit) {
        AtomicReference<Either<String, String>> result = new AtomicReference<>();
        resourceDao.editResource(toEdit)
                .peek(affectedRows -> {
                    if (affectedRows > 0){
                        result.set(Either.right("Recurso modificado correctamente"));
                    }else {
                        result.set(Either.left("El recurso indicado no existe"));
                    }
                })
                .peekLeft(errorMessage -> result.set(Either.left(errorMessage)));
        return result.get();
    }

    @Override
    public Either<String, List<ResourceLiteGetDTO>> getAllResourcesOfClassroom(String classroomId, String resourceType) {
        if (resourceType == null || resourceType.isBlank() || resourceType.isEmpty()){
            resourceType = "%%";
        }else if (!Arrays.stream(ResourceType.values()).map(ResourceType::getStringValue).collect(Collectors.toList()).contains(resourceType)){
            return Either.left("El string de ResourceType no es válido");
        }
        return resourceDao.getAllResourcesOfClassroom(classroomId, resourceType);
    }

    @Override
    public Either<String, ResourceDetailGetDTO> getDetailResource(String resourceUUID) {
        return resourceDao.getDetailResource(resourceUUID);
    }

    @Override
    public Either<String, ResourceComment> insertComment(ResourceCommentPost resourceComment) {
        return resourceDao.postNewComment(resourceComment);
    }

    @Override
    public Either<String, File> getResourceFile(String fileName, String username) {
        return resourceDao.getResourceFile(fileName, username);
    }

    @Override
    public String deleteResource(String resourceUUID) {
        return resourceDao.deleteResource(resourceUUID);
    }
}
