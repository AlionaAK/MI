package com.example.dao.mapper;

import com.example.dao.core.RowMapper;
import com.example.dao.mapper.builder.impl.SegmentRowMapperBuilder;
import entity.Segment;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SegmentRowMapper implements RowMapper<Segment> {

    private static final int SEGMENT_ID = 1;
    private static final int SEGMENT_NAME = 2;

    @Override
    public Segment mapRow(ResultSet set) throws SQLException {
        return
                new SegmentRowMapperBuilder(SEGMENT_ID, SEGMENT_NAME)
                        .getBuiltEntity(set);
    }
}
