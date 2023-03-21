package com.example.domain.command.impl;

import com.example.domain.command.Command;
import com.example.domain.command.Commands;
import com.example.domain.connector.Connection;
import com.example.domain.message.Message;
import com.example.starter.ControllerManager;
import com.example.domain.configuration.Pages;
import entity.User;

public class SingInCommand implements Command {

    private final Connection connection;

    public SingInCommand(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Message execute(Message request) {

        request.setCommand(Commands.SIGN_IN);
        Message response = connection.makeDialog(request);
        User user = (User) response.getByKey("user");

        if (user != null) {
            ControllerManager.setUser(user);
            ControllerManager.changeScene(Pages.USER_PERSONAL_CABINET_PAGE);
        }
        return response;
    }


}
