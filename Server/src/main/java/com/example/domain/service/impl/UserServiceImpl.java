package com.example.domain.service.impl;

import com.example.dao.UserRepository;
import com.example.dao.exception.DAOException;
import com.example.dao.factory.RepositoryFactory;
import com.example.domain.service.UserService;
import com.example.domain.service.exception.ServiceException;
import entity.User;
import entity.UserStatus;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.List;

public class UserServiceImpl implements UserService {

    private final UserRepository userRepository = RepositoryFactory.getInstance().getUserRepository();

    @Override
    public void addNewUser(User user) throws ServiceException {
        try {
            String hashingPassword = DigestUtils.sha256Hex(user.getPassword()).substring(0, 40);
            user.setPassword(hashingPassword);
            userRepository.add(user);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public void updateUser(User user) throws ServiceException {
        try {
            userRepository.update(user);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public void updateUserPassword(String userId, String curPassword,
                                   String newPassword, String confNewPass) throws ServiceException {

        try {
            User user = userRepository.getEntityById(userId);
            String curHashingPassword = DigestUtils.sha256Hex(curPassword).substring(0, 40);
            if (!user.getPassword().equals(curHashingPassword)) {
                throw new ServiceException("Ваш текущий пароль неверен!");
            }
            String newHashingPassword = DigestUtils.sha256Hex(newPassword).substring(0, 40);
            userRepository.updateUserPassword(userId, newHashingPassword);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteUser(String id) throws ServiceException {
        try {
            userRepository.removeById(id);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public List<User> getAll() throws ServiceException {
        try {
            return userRepository.getAll();
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public User signIn(String login, String password) throws ServiceException {
        try {
            String hashingPassword = DigestUtils.sha256Hex(password).substring(0, 40);
            return userRepository.getPatientByLoginAndPassword(login, hashingPassword);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
    }


    @Override
    public void updateUserStatus(String userId, UserStatus status) throws ServiceException {
        try {
            userRepository.updateUserStatus(userId, status);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
    }
}
