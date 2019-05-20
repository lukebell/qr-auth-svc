package ar.com.svc.qr.model.mapper;

import ar.com.svc.qr.model.entity.Config;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import static ar.com.svc.qr.model.constant.ConfigConstants.*;

/**
 * Config RowMapper implementation
 */
public class ConfigRowMapper implements RowMapper<Config> {
    @Override
    public Config mapRow(ResultSet rs, int rowNum) throws SQLException {
        Config config = new Config();

        config.setId(rs.getLong(ID_COLUMN.getValue()));
        config.setClientCode(rs.getString(CLIENT_CODE_COLUMN.getValue()));
        config.setAuthURL(rs.getString(AUTH_URL_COLUMN.getValue()));
        config.setValidateURL(rs.getString(VALIDATE_URL_COLUMN.getValue()));
        config.setSize(rs.getInt(SIZE_COLUMN.getValue()));
        config.setTtl(rs.getLong(TTL_COLUMNN.getValue()));
        config.setCreatedBy(rs.getLong(CREATED_BY_COLUMN.getValue()));
        config.setCreatedDate(rs.getTimestamp(CREATED_DATE_COLUMN.getValue()));

        return config;
    }

}