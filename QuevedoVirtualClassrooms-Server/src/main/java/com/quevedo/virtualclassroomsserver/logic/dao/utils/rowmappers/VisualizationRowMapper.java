package com.quevedo.virtualclassroomsserver.logic.dao.utils.rowmappers;

import com.quevedo.virtualclassroomsserver.common.models.server.user.UserVisualization;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class VisualizationRowMapper implements RowMapper<UserVisualization> {
    @Override
    public UserVisualization mapRow(ResultSet rs, int rowNum) throws SQLException {
        return UserVisualization.builder()
                .username(rs.getString("USERNAME"))
                .resource(UUID.fromString(rs.getString("UUID_RESOURCE")))
                .dateTime(rs.getTimestamp("TIMESTAMP").toLocalDateTime())
                .build();
    }
}
