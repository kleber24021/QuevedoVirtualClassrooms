package com.quevedo.virtualclassroomsserver.logic.dao.utils.rowmappers;

import com.quevedo.virtualclassroomsserver.common.models.server.classroom.Classroom;
import com.quevedo.virtualclassroomsserver.common.models.server.user.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class ClassroomRowMapper implements RowMapper<Classroom> {

    @Override
    public Classroom mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Classroom.builder()
                .uuidClassroom(UUID.fromString(rs.getString("UUID_CLASSROOM")))
                .admin(User.builder().username(rs.getString("ADMIN_ID")).build())
                .name(rs.getString("NAME"))
                .course(rs.getString("COURSE"))
                .build();
    }
}
