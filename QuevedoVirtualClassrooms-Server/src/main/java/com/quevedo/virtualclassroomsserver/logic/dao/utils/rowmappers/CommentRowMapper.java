package com.quevedo.virtualclassroomsserver.logic.dao.utils.rowmappers;


import com.quevedo.virtualclassroomsserver.common.models.server.resource.ResourceComment;
import org.springframework.jdbc.core.RowMapper;
import com.quevedo.virtualclassroomsserver.logic.dao.utils.SqlConstants;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CommentRowMapper implements RowMapper<ResourceComment> {

    @Override
    public ResourceComment mapRow(ResultSet rs, int rowNum) throws SQLException {
        ResourceComment resourceComment = ResourceComment.builder()
                .idComment(rs.getInt(SqlConstants.COMMENT_ID))
                .text(rs.getString(SqlConstants.TEXT))
                .usernameOwner(rs.getString(SqlConstants.USER))
                .timeStamp(rs.getTimestamp(SqlConstants.TIMESTAMP).toLocalDateTime())
                .build();
        if (rs.getString(SqlConstants.ANSWERS_TO) != null){
            resourceComment.setAnswersTo(rs.getInt(SqlConstants.ANSWERS_TO));
        }
        return resourceComment;
    }
}
