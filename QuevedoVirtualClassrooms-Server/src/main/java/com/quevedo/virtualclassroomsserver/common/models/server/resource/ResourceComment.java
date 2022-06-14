package com.quevedo.virtualclassroomsserver.common.models.server.resource;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ResourceComment {
    private int idComment;
    private String text;
    private String usernameOwner;
    private LocalDateTime timeStamp;
    private int answersTo;

}
