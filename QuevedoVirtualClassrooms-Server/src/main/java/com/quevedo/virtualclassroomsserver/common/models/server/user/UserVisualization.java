package com.quevedo.virtualclassroomsserver.common.models.server.user;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class UserVisualization {
    private String username;
    private UUID resource;
    private LocalDateTime dateTime;
}
