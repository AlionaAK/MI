package com.example.domain.command.impl.user;

import com.example.domain.command.Command;
import com.example.domain.message.Message;
import com.example.domain.service.UserService;
import com.example.domain.service.exception.ServiceException;
import com.example.domain.service.factory.ServiceFactory;

public class UpdateUserPasswordCommand implements Command {


    private final UserService userService = ServiceFactory.getInstance().getUserService();


    @Override
    public Message execute(Message request) {
        String userId = (String) request.getByKey("userId");
        String curPass = (String) request.getByKey("curPass");
        String newPass = (String) request.getByKey("newPass");
        String newPassConf = (String) request.getByKey("newPassConf");

        Message response = new Message();
        //

        try {
            userService.updateUserPassword(userId, curPass, newPass, newPassConf);
        } catch (ServiceException e) {
            response.add("ex", e.getMessage());
        }

        return response;
    }


}
