package com.example.domain.command.impl.user;

import com.example.domain.command.Command;
import com.example.domain.command.Commands;
import com.example.domain.connector.Connection;
import com.example.domain.message.Message;

public class UpdateUserCommand implements Command {

    private Connection connection;

    public UpdateUserCommand(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Message execute(Message request) {
        request.setCommand(Commands.UPDATE_USER);
        return connection.makeDialog(request);
    }
}
