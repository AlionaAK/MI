package com.example.domain.command.impl.segment;

import com.example.domain.command.Command;
import com.example.domain.message.Message;
import com.example.domain.service.SegmentService;
import com.example.domain.service.exception.ServiceException;
import com.example.domain.service.factory.ServiceFactory;
import entity.Segment;

public class UpdateSegmentCommand implements Command {

    private SegmentService segmentService = ServiceFactory.getInstance().getSegmentService();

    @Override
    public Message execute(Message request) {
        Segment segment = (Segment) request.getByKey("segment");

        Message response = new Message();
        try {
            segmentService.updateSegment(segment);
        } catch (ServiceException e) {
            e.printStackTrace();
            response.add("ex", e.getMessage());
        }
        return response;
    }

}
