package com.quevedo.virtualclassroomsserver.dao.utils.rowmappers;

import com.quevedo.virtualclassroomsserver.common.models.common.ResourceType;
import com.quevedo.virtualclassroomsserver.common.models.server.resource.Resource;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;


public class ResourceRowMapper implements RowMapper<Resource> {

    @Override
    public Resource mapRow(ResultSet rs, int rowNum) throws SQLException {
        Resource resource = new Resource();
        resource.setUuidResource(UUID.fromString(rs.getString("UUID_RESOURCE")));
        resource.setResourceName(rs.getString("RESOURCE_NAME"));
        resource.setResourceUrl(rs.getString("RESOURCE_URL"));
        resource.setTimestamp(rs.getTimestamp("TIMESTAMP").toLocalDateTime());
        resource.setClassroomUUID(UUID.fromString(rs.getString("UUID_CLASSROOM")));
        resource.setResourceType(ResourceType.getTypeByInt(rs.getInt("RESOURCE_TYPE")));
        return resource;
    }
}
