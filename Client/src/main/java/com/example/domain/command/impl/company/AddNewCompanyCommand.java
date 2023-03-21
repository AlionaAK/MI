package com.example.domain.command.impl.company;

import com.example.domain.command.Command;
import com.example.domain.command.Commands;
import com.example.domain.connector.Connection;
import com.example.domain.message.Message;

public class AddNewCompanyCommand implements Command {

    private Connection connection;

    public AddNewCompanyCommand(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Message execute(Message request) {
        request.setCommand(Commands.ADD_NEW_COMPANY);
        return connection.makeDialog(request);
    }
}
