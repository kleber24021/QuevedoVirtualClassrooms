package com.quevedo.virtualclassroomsserver.common.models.dto.resource;

import com.quevedo.virtualclassroomsserver.common.models.common.ResourceType;
import lombok.Data;

@Data
public class ResourcePutDTO {
    private String uuidResource;
    private String resourceName;
    private String classroomUUID;
    private ResourceType resourceType;
}
