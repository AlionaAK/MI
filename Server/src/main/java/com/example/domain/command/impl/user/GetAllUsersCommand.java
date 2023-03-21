package com.example.domain.command.impl.user;

import com.example.domain.command.Command;
import com.example.domain.message.Message;
import com.example.domain.service.UserService;
import com.example.domain.service.exception.ServiceException;
import com.example.domain.service.factory.ServiceFactory;
import entity.User;

import java.util.List;

public class GetAllUsersCommand implements Command {

    private UserService userService = ServiceFactory.getInstance().getUserService();

    @Override
    public Message execute(Message request) {
        Message response = new Message();
        try {
            List<User> users = userService.getAll();
            response.add("users", users);
        } catch (ServiceException e) {
            e.printStackTrace();
            response.add("ex", e.getMessage());
        }

        return response;
    }

}
