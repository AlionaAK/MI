package com.example.domain.command.impl;

import com.example.domain.command.Command;
import com.example.domain.message.Message;
import com.example.domain.service.UserService;
import com.example.domain.service.exception.ServiceException;
import com.example.domain.service.factory.ServiceFactory;
import entity.User;

public class SignInCommand implements Command {

    private UserService userService = ServiceFactory.getInstance().getUserService();

    @Override
    public Message execute(Message request) {

        final String login = (String) request.getByKey("login");
        final String password = (String) request.getByKey("password");

        final Message response = new Message();

        try {
            User user = userService.signIn(login, password);
            if (user != null) {
                response.add("user", user);
            } else {
                response.add("ex", "Неверный логин или пароль!");
            }

        } catch (ServiceException e) {
            e.printStackTrace();
            response.add("ex", e.getMessage());
        }

        return response;
    }

}
