package com.example.dao.impl;

import com.example.dao.core.InitializerRepository;
import com.example.dao.core.exception.JdbcTemplateException;
import com.example.dao.exception.DAOException;

public class DAOUtil extends InitializerRepository {

    private static final DAOUtil instance = new DAOUtil();

    public static DAOUtil getInstance() {
        return instance;
    }

    private DAOUtil() {

    }



    private static final String GET_USER_BY_LOGIN =
            "select user_id from users where login=?";




    public boolean isLoginUsed(String login) throws DAOException {
        return doIsLoginUsed(login);
    }

    private boolean doIsLoginUsed(String login) throws DAOException {
        try {
            return jdbcTemplate.queryForObject(GET_USER_BY_LOGIN, login);
        } catch (JdbcTemplateException e) {
            throw new DAOException(e);
        }
    }




}
