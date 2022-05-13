package com.quevedo.virtualclassroomsserver.common.models.server.resource;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
public class ResourceComment {
    private UUID uuidComment;
    private String text;
    private String usernameOwner;
    private LocalDateTime timeStamp;
    private boolean isAnswer;
    private UUID answersTo;
    private List<ResourceComment> answers;
}
