package ar.com.svc.qr.model.mapper;

import ar.com.svc.qr.controller.constant.UserRoles;
import ar.com.svc.qr.model.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import static ar.com.svc.qr.model.constant.UserConstants.*;

/**
 * User RowMapper implementation
 */
public class UserRowMapper implements RowMapper<User> {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserRowMapper.class);

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();

        user.setId(rs.getLong(ID_COLUMN.getValue()));
        user.setFullName(rs.getString(FULL_NAME_COLUMN.getValue()));
        user.setUsername(rs.getString(USERNAME_COLUMN.getValue()));
        user.setEmail(rs.getString(EMAIL_COLUMN.getValue()));
        user.setCreatedDate(rs.getTimestamp(CREATED_DATE_COLUMN.getValue()));
        user.setRole(UserRoles.getUserRole(rs.getInt(ROLE_COLUMN.getValue())));

        return user;
    }

}