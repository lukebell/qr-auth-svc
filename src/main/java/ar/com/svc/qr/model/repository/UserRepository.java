package ar.com.svc.qr.model.repository;

import ar.com.svc.qr.exception.AuthException;
import ar.com.svc.qr.model.entity.User;
import ar.com.svc.qr.model.mapper.UserRowMapper;
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

import static ar.com.svc.qr.model.constant.UserConstants.*;

@Repository
public class UserRepository extends BaseRepository<User> {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserRepository.class);

    @Override
    public List<User> findAll() {
        try {
            return jdbcTemplate.query(FIND_ALL_QUERY.getValue(), new UserRowMapper());
        } catch (Exception e) {
            LOGGER.error("Error: " + e.getMessage());
            throw new AuthException();
        }
    }

    @Override
    public User findById(Long id) {
        try {
            return jdbcTemplate.queryForObject(FIND_BY_ID_QUERY.getValue(), new Object[]{id}, new UserRowMapper());
        } catch (Exception e) {
            LOGGER.error("Error: " + e.getMessage());
            throw new AuthException();
        }
    }

    @Transactional
    public User findByUsername(String username) {
        try {
            return jdbcTemplate.queryForObject(FIND_BY_USERNAME_QUERY.getValue(), new Object[]{username}, new UserRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (Exception e) {
            LOGGER.error("Error: " + e.getMessage());
            throw new AuthException();
        }
    }

    @Transactional
    public Boolean exists(String username) {
        try {
            Integer result = jdbcTemplate.queryForObject(EXISTS_QUERY.getValue(), new Object[]{username}, Integer.class);
            return result != null && result > 0;
        } catch (Exception e) {
            LOGGER.error("Error: " + e.getMessage());
            throw new AuthException();
        }
    }

    @Override
    public User update(User user) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int updateById(Long id, User user) {
        try {
            return jdbcTemplate.update(UPDATE_BY_ID_QUERY.getValue(),
                    user.getFullName(),
                    user.getEmail(),
                    user.getRole().getId(),
                    id);
        } catch (Exception e) {
            LOGGER.error("Error: " + e.getMessage());
            throw new AuthException();
        }
    }

    @Transactional
    public int updateByUsername(User user) {
        try {
            return jdbcTemplate.update(UPDATE_BY_USERNAME_QUERY.getValue(),
                    user.getFullName(),
                    user.getEmail(),
                    user.getRole().getId(),
                    user.getUsername());
        } catch (Exception e) {
            LOGGER.error("Error: " + e.getMessage());
            throw new AuthException();
        }
    }

    @Transactional
    public User validate(User user) {
        try {
            return jdbcTemplate.queryForObject(VALIDATE_USER_QUERY.getValue(),
                    new Object[]{
                            user.getUsername(),
                            user.getPassword()
                    }, new UserRowMapper());
        } catch (EmptyResultDataAccessException e) {
            throw new AuthException();
        } catch (Exception e) {
            LOGGER.error("Error: " + e.getMessage());
            throw new AuthException();
        }
    }

    @Override
    public User create(final User user, final String query) {
        try {
            String insertQuery = StringUtils.isBlank(query) ? INSERT_QUERY.getValue() : query;
            KeyHolder holder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, user.getUsername());
                ps.setString(2, user.getFullName());
                ps.setString(3, user.getPassword());
                ps.setString(4, user.getEmail());
                ps.setInt(5, user.getRole().getId());

                return ps;
            }, holder);

            user.setId(holder.getKey().longValue());
            return user;
        } catch (Exception e) {
            LOGGER.error("Error: " + e.getMessage());
            throw new AuthException();
        }
    }

    @Transactional
    public void createBatch(final List<User> users) throws SQLException {
        try {
            jdbcTemplate.batchUpdate(INSERT_QUERY.getValue(), new BatchPreparedStatementSetter() {

                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    User user = users.get(i);
                    ps.setString(1, user.getUsername());
                    ps.setString(2, user.getFullName());
                    ps.setString(3, user.getPassword());
                    ps.setString(4, user.getEmail());
                    ps.setInt(5, user.getRole().getId());
                }

                @Override
                public int getBatchSize() {
                    return users.size();
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
    public int deleteById(Long id) {
        try {
            return jdbcTemplate.update(DELETE_BY_ID_QUERY.getValue(), id);
        } catch (Exception e) {
            LOGGER.error("Error: " + e.getMessage());
            throw new AuthException();
        }
    }

    @Transactional
    public void deleteByUsername(String clientCode) {
        try {
            jdbcTemplate.update(DELETE_BY_USERNAME_QUERY.getValue(), clientCode);
        } catch (Exception e) {
            LOGGER.error("Error: " + e.getMessage());
            throw new AuthException();
        }
    }

}
