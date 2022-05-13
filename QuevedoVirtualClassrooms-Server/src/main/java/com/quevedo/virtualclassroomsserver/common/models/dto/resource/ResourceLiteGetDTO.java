package com.quevedo.virtualclassroomsserver.common.models.dto.resource;

import lombok.Builder;
import lombok.Data;

import com.quevedo.virtualclassroomsserver.common.models.common.ResourceType;
import java.time.LocalDateTime;

@Data
@Builder
public class ResourceLiteGetDTO {
    private String uuidResource;
    private String resourceName;
    private String resourceUrl;
    private LocalDateTime timestamp;
    private ResourceType resourceType;
}
