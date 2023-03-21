package com.example.dao.impl.segment;

import com.example.dao.SegmentRepository;
import com.example.dao.core.InitializerRepository;
import com.example.dao.core.exception.JdbcTemplateException;
import com.example.dao.exception.DAOException;
import com.example.dao.mapper.SegmentRowMapper;
import entity.Segment;

import java.util.List;

public class SegmentSqlRepository extends InitializerRepository implements SegmentRepository {


    @Override
    public Segment getEntityById(String id) throws DAOException {
        try {
            return jdbcTemplate.queryForObject(SegmentSqlUtil.GET_SEGMENT_BY_ID, new SegmentRowMapper(), id);
        } catch (JdbcTemplateException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public boolean removeById(String id) throws DAOException {
        try {
            jdbcTemplate.update(SegmentSqlUtil.DELETE_SEGMENT, id);
            return true;
        } catch (JdbcTemplateException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public boolean add(Segment segment) throws DAOException {
        try {
            jdbcTemplate.update(SegmentSqlUtil.ADD_NEW_SEGMENT,
                    segment.getName());
            return true;
        } catch (JdbcTemplateException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public boolean update(Segment segment) throws DAOException {
        try {
            jdbcTemplate.update(SegmentSqlUtil.UPDATE_SEGMENT,
                    segment.getName(),
                    segment.getId());
            return true;
        } catch (JdbcTemplateException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public List<Segment> getAll() throws DAOException {
        try {
            return jdbcTemplate.query(SegmentSqlUtil.GET_ALL_SEGMENTS, new SegmentRowMapper());
        } catch (JdbcTemplateException e) {
            throw new DAOException(e);
        }
    }
}
