package com.quevedo.virtualclassroomsserver.logic.dao.utils.rowmappers;

import com.quevedo.virtualclassroomsserver.common.models.common.ResourceType;
import com.quevedo.virtualclassroomsserver.common.models.server.resource.Resource;
import com.quevedo.virtualclassroomsserver.logic.dao.utils.SqlConstants;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;


public class ResourceRowMapper implements RowMapper<Resource> {

    @Override
    public Resource mapRow(ResultSet rs, int rowNum) throws SQLException {
        Resource resource = new Resource();
        resource.setUuidResource(UUID.fromString(rs.getString(SqlConstants.UUID_RESOURCE)));
        resource.setResourceName(rs.getString(SqlConstants.RESOURCE_NAME));
        resource.setResourceEndpoint(rs.getString(SqlConstants.RESOURCE_ENDPOINT));
        resource.setTimestamp(rs.getTimestamp(SqlConstants.TIMESTAMP).toLocalDateTime());
        resource.setClassroomUUID(UUID.fromString(rs.getString(SqlConstants.UUID_CLASSROOM)));
        resource.setResourceType(ResourceType.getTypeByInt(rs.getInt(SqlConstants.RESOURCE_TYPE)));
        resource.setComments(new ArrayList<>());
        return resource;
    }
}
