package com.example.domain.command.impl.segment;

import com.example.domain.command.Command;
import com.example.domain.command.Commands;
import com.example.domain.connector.Connection;
import com.example.domain.message.Message;

public class AddNewSegmentCommand implements Command {
    private Connection connection;

    public AddNewSegmentCommand(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Message execute(Message request) {
        request.setCommand(Commands.ADD_NEW_SEGMENT);

        Message response = connection.makeDialog(request);

        return response;
    }
}
