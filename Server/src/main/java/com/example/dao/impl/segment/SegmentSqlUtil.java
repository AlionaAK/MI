package com.example.dao.impl.segment;

final class SegmentSqlUtil {

    private SegmentSqlUtil() {

    }

    static final String UPDATE_SEGMENT =
            "update segments set segment_name=?" +
                    " where segment_id=?";

    static final String ADD_NEW_SEGMENT =
            "insert into segments(segment_id, segment_name)" +
                    " values(UUID(), ?);";

    static final String DELETE_SEGMENT =
            "delete from segments where segment_id=?";

    static final String GET_ALL_SEGMENTS =
            "select segment_id, segment_name from segments";

    static final String GET_SEGMENT_BY_ID =
            "select segment_id, segment_name from segments" +
                    " where segment_id=?";
}
