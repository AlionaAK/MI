package com.example.dao.mapper;

import com.example.dao.core.RowMapper;
import com.example.dao.mapper.builder.impl.UserRowMapperBuilder;
import entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<User> {


    private static final int USER_ID = 1;
    private static final int USER_STATUS_ID = 2;
    private static final int USER_LOGIN = 3;
    private static final int USER_PASSWORD = 4;
    private static final int USER_NAME = 5;
    private static final int USER_SURNAME = 6;
    private static final int USER_PHONE = 7;


    @Override
    public User mapRow(ResultSet set) throws SQLException {
        return
                new UserRowMapperBuilder(USER_ID, USER_STATUS_ID, USER_LOGIN, USER_PASSWORD, USER_NAME,
                        USER_SURNAME, USER_PHONE)
                        .getBuiltEntity(set);
    }
}
