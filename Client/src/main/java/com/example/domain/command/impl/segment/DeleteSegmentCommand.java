package com.example.domain.command.impl.segment;

import com.example.domain.command.Command;
import com.example.domain.command.Commands;
import com.example.domain.connector.Connection;
import com.example.domain.message.Message;

public class DeleteSegmentCommand implements Command {
    private Connection connection;

    public DeleteSegmentCommand(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Message execute(Message request) {
        request.setCommand(Commands.DELETE_SEGMENT);
        return connection.makeDialog(request);
    }
}
