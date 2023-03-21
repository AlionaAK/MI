package com.example.domain.command.impl.segment;

import com.example.domain.command.Command;
import com.example.domain.message.Message;
import com.example.domain.service.SegmentService;
import com.example.domain.service.exception.ServiceException;
import com.example.domain.service.factory.ServiceFactory;

public class DeleteSegmentCommand implements Command {
    private SegmentService segmentService = ServiceFactory.getInstance().getSegmentService();

    @Override
    public Message execute(Message request) {
        Message response = new Message();
        String segmentId = (String) request.getByKey("segmentId");

        try {
            segmentService.deleteSegment(segmentId);
        } catch (ServiceException e) {
            response.add("ex", "can`t delete cateory");
        }
        return response;
    }


}
