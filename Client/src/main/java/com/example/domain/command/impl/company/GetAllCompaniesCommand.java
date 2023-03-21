package com.example.domain.command.impl.company;

import com.example.domain.command.Command;
import com.example.domain.command.Commands;
import com.example.domain.connector.Connection;
import com.example.domain.message.Message;
import entity.Company;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class GetAllCompaniesCommand implements Command {

    private Connection connection;

    public GetAllCompaniesCommand(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Message execute(Message request) {
        request.setCommand(Commands.GET_ALL_COMPANIES);
        Message response = connection.makeDialog(request);
        List<Company> companies = (List<Company>) response.getByKey("companies");

        ObservableList<Company> companyObservableList = FXCollections.observableList(companies);

        response.add("companies", companyObservableList);

        return response;
    }
}
