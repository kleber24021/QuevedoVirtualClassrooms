package com.quevedo.virtualclassroomsserver.implementations.dao.utils.rowmappers;

import com.quevedo.virtualclassroomsserver.common.exceptions.UnexpectedEnumValueException;
import com.quevedo.virtualclassroomsserver.common.models.common.UserType;
import com.quevedo.virtualclassroomsserver.common.models.server.user.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        try {
            return User.builder()
                    .username(rs.getString("USERNAME"))
                    .name(rs.getString("NAME"))
                    .surname(rs.getString("SURNAME"))
                    .profileImage(rs.getString("PROFILE_IMAGE_URL"))
                    .hashedPassword(rs.getString("HASH_PASSWORD"))
                    .userType(UserType.getEnumFromValue(rs.getInt("USER_TYPE")))
                    .build();
        } catch (UnexpectedEnumValueException unexpectedEnumValueException) {
            throw new SQLException("Not valid method for User Type");
        }
    }
}
