package com.example.domain.command.impl.user;

import com.example.domain.command.Command;
import com.example.domain.message.Message;
import com.example.domain.service.UserService;
import com.example.domain.service.exception.ServiceException;
import com.example.domain.service.factory.ServiceFactory;
import com.example.domain.util.builder.impl.UserBuilderImpl;
import entity.User;
import entity.UserStatus;

public class SignUpCommand implements Command {

    private final UserService userService = ServiceFactory.getInstance().getUserService();

    @Override
    public Message execute(Message request) {
        final String login = (String) request.getByKey("login");
        final String password = (String) request.getByKey("password");
        final String name = (String) request.getByKey("name");
        final String surname = (String) request.getByKey("surname");
        final String phone = (String) request.getByKey("phone");
        final UserStatus status = (UserStatus) request.getByKey("userStatus");

        final User user = new UserBuilderImpl()
                .withUserStatus(status)
                .withLogin(login)
                .withPassword(password)
                .withName(name)
                .withSurname(surname)
                .withPhone(phone)
                .build();

        final Message response = new Message();
        try {
            userService.addNewUser(user);
        } catch (ServiceException e) {
            e.printStackTrace();
            response.add("ex", e.getMessage());
        }

        return response;
    }


}
