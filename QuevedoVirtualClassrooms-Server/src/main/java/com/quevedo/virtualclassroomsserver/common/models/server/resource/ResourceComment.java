package com.quevedo.virtualclassroomsserver.common.models.server.resource;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
public class ResourceComment {
    private UUID uuidComment;
    private String text;
    private String usernameOwner;
    private LocalDateTime timeStamp;
    private boolean isAnswer;
    private UUID answersTo;
    private List<ResourceComment> answers;

    public boolean pushCommentToAnswers(ResourceComment resourceComment){
        return answers.add(resourceComment);
    }
}
