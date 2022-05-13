package com.quevedo.virtualclassroomsserver.common.models.server.resource;

import com.quevedo.virtualclassroomsserver.common.models.common.ResourceType;
import com.quevedo.virtualclassroomsserver.common.models.dto.resource.ResourceCommentDTO;
import com.quevedo.virtualclassroomsserver.common.models.dto.resource.ResourceDetailGetDTO;
import com.quevedo.virtualclassroomsserver.common.models.dto.resource.ResourceLiteGetDTO;
import com.quevedo.virtualclassroomsserver.common.models.server.user.UserVisualization;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
public class Resource {
    private UUID uuidResource;
    private String resourceName;
    private String resourceUrl;
    private LocalDateTime timestamp;
    private UUID classroomUUID;
    private ResourceType resourceType;
    private List<ResourceComment> comments = new ArrayList<>();
    private List<UserVisualization> visualizations = new ArrayList<>();

    public ResourceLiteGetDTO toLiteDTO() {
        return ResourceLiteGetDTO.builder()
                .resourceName(this.resourceName)
                .uuidResource(this.uuidResource.toString())
                .resourceUrl(this.resourceUrl)
                .resourceType(this.resourceType)
                .timestamp(this.timestamp)
                .build();
    }

    public ResourceDetailGetDTO toDetailDTO() {
        return ResourceDetailGetDTO.builder()
                .uuidResource(this.uuidResource.toString())
                .resourceName(this.resourceName)
                .resourceUrl(this.resourceUrl)
                .timestamp(this.timestamp)
                .classroomUUID(this.classroomUUID.toString())
                .resourceType(this.resourceType)
                .comments(
                        this.comments.stream()
                                .map(comment ->
                                        ResourceCommentDTO.builder()
                                        .usernameOwner(comment.getUsernameOwner())
                                        .text(comment.getText())
                                        .timeStamp(comment.getTimeStamp())
                                        .isAnswer(comment.isAnswer())
                                        .answersTo(comment.getAnswersTo().toString())
                                        .resourceUUID(this.uuidResource.toString())
                                        .build()
                                ).collect(Collectors.toList())
                ).build();
    }
}
