package com.example.domain.command.impl.segment;

import com.example.domain.command.Command;
import com.example.domain.message.Message;
import com.example.domain.service.SegmentService;
import com.example.domain.service.exception.ServiceException;
import com.example.domain.service.factory.ServiceFactory;
import entity.Segment;

import java.util.List;

public class GetAllSegmentsCommand implements Command {

    private SegmentService segmentService = ServiceFactory.getInstance().getSegmentService();

    @Override
    public Message execute(Message request) {
        Message response = new Message();
        try {
            List<Segment> segments = segmentService.getAllSegments();
            response.add("segments", segments);
        } catch (ServiceException e) {
            response.add("ex", "can`t get all departments");
        }
        return response;
    }


}
