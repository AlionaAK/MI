package com.example.dao.core.impl;



import com.example.dao.core.ResultSetExtractor;
import com.example.dao.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class realise ResultSetExtractor
 * and doing extract logic from ResultSet
 *
 * @param <T>
 */
class RowMapperResultSetExtractor<T> implements ResultSetExtractor<List<T>> {

    private final RowMapper<T> rowMapper;


    RowMapperResultSetExtractor(RowMapper<T> rowMapper) {
        this.rowMapper = rowMapper;
    }

    @Override
    public List<T> extractData(ResultSet set) throws SQLException {

        final List<T> results = new ArrayList<>();

        while (set.next()) {
            results.add(this.rowMapper.mapRow(set));
        }

        return results;
    }
}
