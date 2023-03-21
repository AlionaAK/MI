package com.example.domain.command.impl.user;

import com.example.domain.command.Command;
import com.example.domain.command.Commands;
import com.example.domain.connector.Connection;
import com.example.domain.message.Message;
import com.example.starter.ControllerManager;
import com.example.domain.configuration.Pages;

public class SignUpCommand implements Command {

    private final Connection connection;

    public SignUpCommand(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Message execute(Message request) {

        request.setCommand(Commands.SIGN_UP);

        Message response = connection.makeDialog(request);

        String exception = (String) response.getByKey("ex");

        if (exception == null) {
            ControllerManager.changeScene(Pages.SIGN_IN_PAGE);
        }


        return response;
    }
}
