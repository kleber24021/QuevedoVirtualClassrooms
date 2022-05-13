package com.quevedo.virtualclassroomsserver.common.models.dto.resource;

import com.quevedo.virtualclassroomsserver.common.models.common.ResourceType;
import lombok.Data;

import java.io.InputStream;
import java.time.LocalDateTime;

@Data
public class ResourcePostDTO {
    private String resourceName;
    private LocalDateTime timestamp;
    private ResourceType resourceType;
    private InputStream fileStream;
    private String classroomUUID;
    private String fileExtension;
}
