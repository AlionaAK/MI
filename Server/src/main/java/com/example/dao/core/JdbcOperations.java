package com.example.dao.core;


import com.example.dao.core.exception.JdbcTemplateException;

import java.util.List;

/**
 * Base interface with GRUD operations and query to information storage
 *
 * @see RowMapper
 * @see JdbcTemplateException
 */
public interface JdbcOperations {

    int update(String sql, Object... argc) throws JdbcTemplateException;

    <T> List<T> query(String sql, RowMapper<T> rowMapper) throws JdbcTemplateException;

    <T> List<T> query(String sql, RowMapper<T> rowMapper, Object... argc) throws JdbcTemplateException;

    <T> T queryForObject(String sql, RowMapper<T> rowMapper, Object... args) throws JdbcTemplateException;

    boolean queryForObject(String sql, Object... argc) throws JdbcTemplateException;
}
