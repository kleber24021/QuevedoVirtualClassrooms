package com.quevedo.quevedovirtualclassroomsserver.models;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class VideoInfoDTO {
    private String id;
    private String name;
    private String url;
    private LocalDateTime uploadTime;
}
