package com.example.domain.command.impl.user;

import com.example.domain.command.Command;
import com.example.domain.message.Message;
import com.example.domain.service.UserService;
import com.example.domain.service.exception.ServiceException;
import com.example.domain.service.factory.ServiceFactory;
import entity.User;

public class UpdateUserCommand implements Command {


    private UserService userService = ServiceFactory.getInstance().getUserService();

    @Override
    public Message execute(Message request) {
        User user = (User) request.getByKey("user");

        Message response = new Message();
        try {
            userService.updateUser(user);
        } catch (ServiceException e) {
            e.printStackTrace();
            response.add("ex", e.getMessage());
        }
        return response;
    }

}
