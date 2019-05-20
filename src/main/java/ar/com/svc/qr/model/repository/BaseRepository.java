package ar.com.svc.qr.model.repository;

import ar.com.svc.qr.model.entity.BaseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;

@Repository
public abstract class BaseRepository<T extends BaseEntity> {

    @Autowired
    protected JdbcTemplate jdbcTemplate;

    @Autowired
    protected NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Transactional(readOnly = true)
    public abstract List<T> findAll();

    @Transactional(readOnly = true)
    public abstract T findById(final Long id);

    @Transactional
    public abstract T update(final T t);

    @Transactional
    public abstract int updateById(final Long id, final T t);

    @Transactional
    public abstract T create(final T t, final String query);

    @Transactional
    public abstract void deleteAll();

    @Transactional
    public abstract int deleteById(final Long id);

}