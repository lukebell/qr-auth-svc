package ar.com.svc.qr.model.mapper;

import ar.com.svc.qr.model.entity.QRCode;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import static ar.com.svc.qr.model.constant.QRCodeConstants.*;

/**
 * QR Code RowMapper implementation
 */
public class QRCodeRowMapper implements RowMapper<QRCode> {
    @Override
    public QRCode mapRow(ResultSet rs, int rowNum) throws SQLException {
        QRCode qrCode = new QRCode();

        qrCode.setId(rs.getLong(ID_COLUMN.getValue()));
        qrCode.setClientCode(rs.getLong(CLIENT_CODE_COLUMN.getValue()));
        qrCode.setToken(rs.getString(TOKEN_COLUMN.getValue()));
        qrCode.setUuid(rs.getString(UUID_COLUMN.getValue()));
        qrCode.setCreatedDate(rs.getTimestamp(CREATED_DATE_COLUMN.getValue()));

        return qrCode;
    }

}