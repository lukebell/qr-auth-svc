package ar.com.svc.qr.model.repository;

import ar.com.svc.qr.exception.AuthException;
import ar.com.svc.qr.model.entity.Config;
import ar.com.svc.qr.model.mapper.ConfigRowMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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

import static ar.com.svc.qr.model.constant.ConfigConstants.*;

@Repository
@CacheConfig(cacheNames = "configs")
public class ConfigRepository extends BaseRepository<Config> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigRepository.class);

    @Override
    public List<Config> findAll() {
        try {
            return jdbcTemplate.query(FIND_ALL_QUERY.getValue(), new ConfigRowMapper());
        } catch (Exception e) {
            LOGGER.error("Error: " + e.getMessage());
            throw new AuthException();
        }
    }

    @Override
    public Config findById(Long id) {
        try {
            return jdbcTemplate.queryForObject(FIND_BY_ID_QUERY.getValue(), new Object[]{id}, new ConfigRowMapper());
        } catch (Exception e) {
            LOGGER.error("Error: " + e.getMessage());
            throw new AuthException();
        }
    }

    @Transactional
    @Cacheable(key = "#clientCode")
    public Config findByClientCode(String clientCode) {
        try {
            return jdbcTemplate.queryForObject(FIND_BY_CLIENT_CODE_QUERY.getValue(), new Object[]{clientCode}, new ConfigRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (Exception e) {
            LOGGER.error("Error: " + e.getMessage());
            throw new AuthException();
        }
    }

    @Transactional
    public Boolean exists(String clientCode) {
        try {
            Integer result = jdbcTemplate.queryForObject(EXISTS_QUERY.getValue(), new Object[]{clientCode}, Integer.class);
            return result != null && result > 0;
        } catch (Exception e) {
            LOGGER.error("Error: " + e.getMessage());
            throw new AuthException();
        }
    }

    @Override
    @CacheEvict(value = "configs", allEntries = true)
    public Config update(Config config) {
        throw new UnsupportedOperationException();
    }

    @Override
    @CacheEvict(value = "configs", allEntries = true)
    public int updateById(Long id, Config config) {
        try {
            return jdbcTemplate.update(UPDATE_BY_ID_QUERY.getValue(),
                    config.getAuthURL(),
                    config.getValidateURL(),
                    config.getSize(),
                    config.getTtl(),
                    id);
        } catch (Exception e) {
            LOGGER.error("Error: " + e.getMessage());
            throw new AuthException();
        }
    }

    @Transactional
    @CacheEvict(value = "configs", allEntries = true)
    public int updateByClientCode(Config config) {
        try {
            return jdbcTemplate.update(UPDATE_BY_CLIENT_CODE_QUERY.getValue(),
                    config.getAuthURL(),
                    config.getValidateURL(),
                    config.getSize(),
                    config.getTtl(),
                    config.getClientCode());
        } catch (Exception e) {
            LOGGER.error("Error: " + e.getMessage());
            throw new AuthException();
        }
    }

    @Override
    @CacheEvict(value = "configs", allEntries = true)
    public Config create(final Config config, final String query) {
        try {
            String insertQuery = StringUtils.isBlank(query) ? INSERT_QUERY.getValue() : query;
            KeyHolder holder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, config.getClientCode());
                ps.setString(2, config.getAuthURL());
                ps.setString(3, config.getValidateURL());
                ps.setInt(4, config.getSize());
                ps.setLong(5, config.getTtl());
                ps.setLong(6, config.getCreatedBy());
                return ps;
            }, holder);

            config.setId(holder.getKey().longValue());
            return config;
        } catch (Exception e) {
            LOGGER.error("Error: " + e.getMessage());
            throw new AuthException();
        }
    }

    @Transactional
    @CacheEvict(value = "configs", allEntries = true)
    public void createBatch(final List<Config> configs) throws SQLException {
        try {
            jdbcTemplate.batchUpdate(INSERT_QUERY.getValue(), new BatchPreparedStatementSetter() {

                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    Config config = configs.get(i);
                    ps.setString(1, config.getClientCode());
                    ps.setString(2, config.getAuthURL());
                    ps.setString(3, config.getValidateURL());
                    ps.setInt(4, config.getSize());
                    ps.setLong(5, config.getTtl());
                    ps.setLong(6, config.getCreatedBy());
                }

                @Override
                public int getBatchSize() {
                    return configs.size();
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
    public void deleteByClientCode(String clientCode) {
        try {
            jdbcTemplate.update(DELETE_BY_CLIENT_CODE_QUERY.getValue(), clientCode);
        } catch (Exception e) {
            LOGGER.error("Error: " + e.getMessage());
            throw new AuthException();
        }
    }

}
