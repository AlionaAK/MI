package com.example.dao.mapper.builder;

import java.sql.ResultSet;
import java.sql.SQLException;



public interface RowMapperBuilder<T> {
    T getBuiltEntity(final ResultSet set) throws SQLException;
}
