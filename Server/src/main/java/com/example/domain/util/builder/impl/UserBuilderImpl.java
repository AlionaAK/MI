package com.example.domain.util.builder.impl;

import com.example.domain.util.builder.UserBuilder;
import entity.User;
import entity.UserStatus;

public class UserBuilderImpl implements UserBuilder {

    private String id;
    private UserStatus status;
    private String login;
    private String password;
    private String name;
    private String surname;
    private String phone;

    public UserBuilderImpl() {
        this.id = "";
    }

    public UserBuilderImpl(String id) {
        this.id = id;
    }


    @Override
    public UserBuilder withLogin(String login) {
        this.login = login;
        return this;
    }

    @Override
    public UserBuilder withPassword(String password) {
        this.password = password;
        return this;
    }

    @Override
    public UserBuilder withName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public UserBuilder withSurname(String surname) {
        this.surname = surname;
        return this;
    }

    @Override
    public UserBuilder withPhone(String phone) {
        this.phone = phone;
        return this;
    }

    @Override
    public UserBuilder withUserStatus(UserStatus status) {
        this.status = status;
        return this;
    }

    @Override
    public User build() {
        final User user = new User();
        user.setId(id);
        user.setUserStatus(status);
        user.setName(name);
        user.setSurname(surname);
        user.setPhone(phone);
        user.setLogin(login);
        user.setPassword(password);
        return user;
    }
}
