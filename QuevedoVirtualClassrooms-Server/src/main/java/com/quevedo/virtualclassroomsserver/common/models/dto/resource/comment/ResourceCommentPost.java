package com.quevedo.virtualclassroomsserver.common.models.dto.resource.comment;

import lombok.Data;

@Data
public class ResourceCommentPost {
    private String text;
    private String username;
    private String resourceId;
    private int answersTo;
}
