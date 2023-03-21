package com.example.dao.impl.user;

import com.example.dao.UserRepository;
import com.example.dao.core.InitializerRepository;
import com.example.dao.core.exception.JdbcTemplateException;
import com.example.dao.exception.DAOException;
import com.example.dao.impl.DAOUtil;
import com.example.dao.mapper.UserRowMapper;
import entity.User;
import entity.UserStatus;

import java.util.List;

import static com.example.dao.impl.user.UserSqlUtil.*;

public class UserSqlRepository extends InitializerRepository implements UserRepository {

    @Override
    public User getEntityById(String id) throws DAOException {
        try {
            return jdbcTemplate.queryForObject(GET_USER_BY_ID, new UserRowMapper(), id);
        } catch (JdbcTemplateException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void updateUserPassword(String userId, String newPassword) throws DAOException {
        try {
            jdbcTemplate.update(UPDATE_USER_PASSWORD, newPassword, userId);
        } catch (JdbcTemplateException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public boolean removeById(String id) throws DAOException {
        try {
            jdbcTemplate.update(DELETE_USER, id);
            return true;
        } catch (JdbcTemplateException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public boolean add(User user) throws DAOException {
        if (DAOUtil.getInstance().isLoginUsed(user.getLogin())) {
            throw new DAOException("Логин уже занят!");
        }
        ///
        try {
            jdbcTemplate.update(ADD_NEW_USER,
                    user.getUserStatus().getId(),
                    user.getLogin(),
                    user.getPassword(),
                    user.getName(),
                    user.getSurname(),
                    user.getPhone());
            return true;
        } catch (JdbcTemplateException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public boolean update(User user) throws DAOException {
        try {
            jdbcTemplate.update(UPDATE_USER,
                    user.getUserStatus().getId(),
                    user.getLogin(),
                    user.getName(),
                    user.getSurname(),
                    user.getPhone(),
                    user.getId());
            return true;
        } catch (JdbcTemplateException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public List<User> getAll() throws DAOException {
        try {
            return jdbcTemplate.query(GET_ALL_USERS, new UserRowMapper());
        } catch (JdbcTemplateException e) {
            throw new DAOException(e);
        }
    }


    @Override
    public User getPatientByLoginAndPassword(String login, String password) throws DAOException {
        try {
            return jdbcTemplate.queryForObject(GET_BY_LOGIN_AND_PASSWORD, new UserRowMapper(), login, password);
        } catch (JdbcTemplateException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void updateUserStatus(String userId, UserStatus status) throws DAOException {
        try {
            jdbcTemplate.update(UPDATE_USER_STATUS, status, userId);
        } catch (JdbcTemplateException e) {
            throw new DAOException(e);
        }
    }
}
