package com.example.domain.command.impl.segment;

import com.example.domain.command.Command;
import com.example.domain.message.Message;
import com.example.domain.service.SegmentService;
import com.example.domain.service.exception.ServiceException;
import com.example.domain.service.factory.ServiceFactory;
import entity.Segment;

public class AddNewSegmentCommand implements Command {

    private SegmentService segmentService = ServiceFactory.getInstance().getSegmentService();


    @Override
    public Message execute(Message request) {
        Message response = new Message();
        Segment segment = (Segment) request.getByKey("segment");

        try {
            segmentService.addNewSegment(segment);
        } catch (ServiceException e) {
            response.add("ex", "can`t add segment");
        }
        return response;
    }


}
