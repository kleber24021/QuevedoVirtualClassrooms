package com.quevedo.virtualclassroomsserver.common.models.dto.resource;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ResourceCommentDTO {
    private String usernameOwner;
    private String text;
    private LocalDateTime timeStamp;
    private boolean isAnswer;
    private String answersTo;
    private String resourceUUID;
}
