package com.example.domain.command.impl.segment;

import com.example.domain.command.Command;
import com.example.domain.command.Commands;
import com.example.domain.connector.Connection;
import com.example.domain.message.Message;
import entity.Segment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class GetAllSegmentsCommand implements Command {
    private Connection connection;

    public GetAllSegmentsCommand(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Message execute(Message request) {
        request.setCommand(Commands.GET_ALL_SEGMENT);

        Message response = connection.makeDialog(request);
        List<Segment> segments = (List<Segment>) response.getByKey("segments");

        ObservableList<Segment> segmentObservableList = FXCollections.observableList(segments);

        response.add("segments", segmentObservableList);

        return response;
    }
}
