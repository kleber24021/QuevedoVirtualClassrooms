package com.quevedo.virtualclassroomsserver.common.models.dto.resource;

import com.quevedo.virtualclassroomsserver.common.models.common.ResourceType;
import com.quevedo.virtualclassroomsserver.common.models.server.resource.ResourceComment;
import com.quevedo.virtualclassroomsserver.common.models.server.user.UserVisualization;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class ResourceDetailGetDTO {
    private String uuidResource;
    private String resourceName;
    private String resourceEndpoint;
    private LocalDateTime timestamp;
    private String classroomUUID;
    private ResourceType resourceType;
    private List<ResourceComment> comments;
    private List<UserVisualization> visualizations;
}
