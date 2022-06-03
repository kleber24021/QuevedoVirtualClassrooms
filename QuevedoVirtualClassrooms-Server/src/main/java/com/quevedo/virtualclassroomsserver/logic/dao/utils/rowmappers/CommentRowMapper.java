package com.quevedo.virtualclassroomsserver.logic.dao.utils.rowmappers;


import com.quevedo.virtualclassroomsserver.common.models.server.resource.ResourceComment;
import org.springframework.jdbc.core.RowMapper;
import com.quevedo.virtualclassroomsserver.logic.dao.utils.SqlConstants;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

public class CommentRowMapper implements RowMapper<ResourceComment> {

    @Override
    public ResourceComment mapRow(ResultSet rs, int rowNum) throws SQLException {
        ResourceComment resourceComment = ResourceComment.builder()
                .uuidComment(UUID.fromString(rs.getString(SqlConstants.COMMENT_ID)))
                .text(rs.getString(SqlConstants.TEXT))
                .usernameOwner(rs.getString(SqlConstants.USER))
                .timeStamp(rs.getTimestamp(SqlConstants.TIMESTAMP).toLocalDateTime())
                .isAnswer(rs.getBoolean(SqlConstants.IS_ANSWER))
                .answers(new ArrayList<>())
                .build();
        if (rs.getString(SqlConstants.ANSWERS_TO) != null){
            resourceComment.setAnswersTo(UUID.fromString(rs.getString(SqlConstants.ANSWERS_TO)));
        }
        return resourceComment;
    }
}
