package com.example.domain.command.impl.user;

import com.example.domain.command.Command;
import com.example.domain.message.Message;
import com.example.domain.service.UserService;
import com.example.domain.service.exception.ServiceException;
import com.example.domain.service.factory.ServiceFactory;

public class DeleteUserCommand implements Command {

    private UserService service = ServiceFactory.getInstance().getUserService();

    @Override
    public Message execute(Message request) {
        String id = (String) request.getByKey("userId");
        Message response = new Message();
        try {
            service.deleteUser(id);
        } catch (ServiceException e) {
            e.printStackTrace();
            response.add("ex", e.getMessage());
        }

        return response;
    }


}
