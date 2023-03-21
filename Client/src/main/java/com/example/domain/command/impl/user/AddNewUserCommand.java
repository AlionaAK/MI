package com.example.domain.command.impl.user;

import com.example.domain.command.Command;
import com.example.domain.command.Commands;
import com.example.domain.connector.Connection;
import com.example.domain.message.Message;

public class AddNewUserCommand implements Command {

    private Connection connection;

    public AddNewUserCommand(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Message execute(Message request)  {

        request.setCommand(Commands.ADD_NEW_USER);

        Message response = connection.makeDialog(request);

        return response;
    }
}
