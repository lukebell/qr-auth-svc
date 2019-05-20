package ar.com.svc.qr.model.repository;

import ar.com.svc.qr.exception.AuthException;
import ar.com.svc.qr.model.entity.QRCode;
import ar.com.svc.qr.model.mapper.QRCodeRowMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static ar.com.svc.qr.model.constant.QRCodeConstants.*;

@Repository
public class QRCodeRepository extends BaseRepository<QRCode> {

    private static final Logger LOGGER = LoggerFactory.getLogger(QRCodeRepository.class);

    @Override
    public List<QRCode> findAll() {
        try {
            return jdbcTemplate.query(FIND_ALL_QUERY.getValue(), new QRCodeRowMapper());
        } catch (Exception e) {
            LOGGER.error("Error: " + e.getMessage());
            throw new AuthException();
        }
    }

    public List<QRCode> findAll(String q) {
        try {
            String query;
            Object[] o = null;
            if(q == null) {
                query =  FIND_ALL_QUERY.getValue();
            } else {
                query =  FIND_ALL_BY_QUERY.getValue();
                o = new Object[]{"%" + q + "%"};
            }
            return jdbcTemplate.query(query, o, new QRCodeRowMapper());
        } catch (Exception e) {
            LOGGER.error("Error: " + e.getMessage());
            throw new AuthException();
        }
    }

    @Override
    public QRCode findById(Long id) {
        try {
            return jdbcTemplate.queryForObject(FIND_BY_ID_QUERY.getValue(), new Object[]{id}, new QRCodeRowMapper());
        } catch (Exception e) {
            LOGGER.error("Error: " + e.getMessage());
            throw new AuthException();
        }
    }

    @Transactional
    public List<QRCode> findByClientCode(Long clientCode, String q) {
        try {
            String query;
            Object[] o;
            if(q == null) {
                query =  FIND_BY_CLIENT_CODE_QUERY.getValue();
                o = new Object[]{clientCode};
            } else {
                query =  FIND_BY_CLIENT_CODE_AND_QUERY.getValue();
                o = new Object[]{clientCode, "%" + q + "%"};
            }

            return jdbcTemplate.query(query, o, new QRCodeRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (Exception e) {
            LOGGER.error("Error: " + e.getMessage());
            throw new AuthException();
        }
    }

    @Transactional
    public QRCode findByClientCodeAndUUID(Long clientCode, String uUID) {
        try {
            return jdbcTemplate.queryForObject(FIND_BY_CLIENT_CODE_AND_UUID_QUERY.getValue(), new Object[]{clientCode, uUID}, new QRCodeRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (Exception e) {
            LOGGER.error("Error: " + e.getMessage());
            throw new AuthException();
        }
    }

    @Transactional
    public QRCode findByClientCodeAndTokenAndUUID(Long clientCode, String token, String uUID) {
        try {
            return jdbcTemplate.queryForObject(FIND_BY_CLIENT_CODE_AND_TOKEN_UUID_QUERY.getValue(), new Object[]{clientCode, token, uUID}, new QRCodeRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (Exception e) {
            LOGGER.error("Error: " + e.getMessage());
            throw new AuthException();
        }
    }

    @Transactional
    public QRCode findByClientCodeAndToken(final String clientCode, final String token) {
        try {
            return jdbcTemplate.queryForObject(FIND_BY_CLIENT_CODE_AND_TOKEN_QUERY.getValue(), new Object[]{clientCode, token}, new QRCodeRowMapper());
        } catch (Exception e) {
            LOGGER.error("Error: " + e.getMessage());
            throw new AuthException();
        }
    }

    @Transactional
    public Boolean exists(final String clientCode) {
        try {
            Integer result = jdbcTemplate.queryForObject(EXISTS_QUERY.getValue(), new Object[]{clientCode}, Integer.class);
            return result != null && result > 0;
        } catch (Exception e) {
            LOGGER.error("Error: " + e.getMessage());
            throw new AuthException();
        }
    }

    @Override
    public QRCode update(final QRCode qrCode) {
        throw new UnsupportedOperationException();
    }

    @Transactional
    public int updateById(final Long id, final QRCode qrCode) {
        try {
            return jdbcTemplate.update(UPDATE_BY_ID_QUERY.getValue(),
                    new Object[]{
                            qrCode.getToken(),
                            qrCode.getUuid(),
                            qrCode.getCreatedDate()
                    }, id);
        } catch (Exception e) {
            LOGGER.error("Error: " + e.getMessage());
            throw new AuthException();
        }
    }

    @Transactional
    public QRCode createOrUpdate(final QRCode qrCode) {
        return create(qrCode, INSERT_OR_UPDATE_QUERY.getValue());
    }

    @Override
    public QRCode create(final QRCode qrCode, final String query) {
        try {
            String insertQuery = StringUtils.isBlank(query) ? INSERT_QUERY.getValue() : query;
            KeyHolder holder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
                ps.setLong(1, qrCode.getClientCode());
                ps.setString(2, qrCode.getToken());
                ps.setString(3, qrCode.getUuid());
                return ps;
            }, holder);

            return qrCode;
        } catch (Exception e) {
            LOGGER.error("Error: " + e.getMessage());
            throw new AuthException();
        }
    }

    @Transactional
    public void createBatch(final List<QRCode> qrCodes) {
        try {
            jdbcTemplate.batchUpdate(INSERT_QUERY.getValue(), new BatchPreparedStatementSetter() {

                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    QRCode qrCode = qrCodes.get(i);
                    ps.setLong(1, qrCode.getClientCode());
                    ps.setString(2, qrCode.getToken());
                    ps.setString(3, qrCode.getUuid());
                }

                @Override
                public int getBatchSize() {
                    return qrCodes.size();
                }
            });
        } catch (Exception e) {
            LOGGER.error("Error: " + e.getMessage());
            throw new AuthException();
        }
    }

    @Override
    public void deleteAll() {
        try {
            jdbcTemplate.update(DELETE_ALL_QUERY.getValue());
        } catch (Exception e) {
            LOGGER.error("Error: " + e.getMessage());
            throw new AuthException();
        }
    }

    @Override
    public int deleteById(final Long id) {
        try {
            return jdbcTemplate.update(DELETE_BY_ID_QUERY.getValue(), id);
        } catch (Exception e) {
            LOGGER.error("Error: " + e.getMessage());
            throw new AuthException();
        }
    }

    @Transactional
    public void deleteByClientCodeAndToken(final String clientCode, final String token) {
        try {
            jdbcTemplate.update(DELETE_BY_CLIENT_CODE_AND_TOKEN_QUERY.getValue(), clientCode, token);
        } catch (Exception e) {
            LOGGER.error("Error: " + e.getMessage());
            throw new AuthException();
        }
    }

}
