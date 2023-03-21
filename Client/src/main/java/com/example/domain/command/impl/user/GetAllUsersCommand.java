package com.example.domain.command.impl.user;

import com.example.domain.command.Command;
import com.example.domain.command.Commands;
import com.example.domain.connector.Connection;
import com.example.domain.message.Message;
import entity.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class GetAllUsersCommand implements Command {

    private Connection connection;

    public GetAllUsersCommand(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Message execute(Message request)  {
        request.setCommand(Commands.GET_ALL_USERS);
        Message response = connection.makeDialog(request);
        List<User> users = (List<User>) response.getByKey("users");

        ObservableList<User> userObservableList = FXCollections.observableList(users);

        response.add("users", userObservableList);

        return response;
    }
}
