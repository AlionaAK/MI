package com.example.controller;

import com.example.domain.command.Command;
import com.example.domain.command.Commands;
import com.example.domain.command.factory.CommandFactory;
import com.example.domain.message.Message;

public final class SendInfoToServer {
    private SendInfoToServer() {
    }


    public static boolean sendInfoToServer(Commands addNewCommand,
                                           String key,
                                           Object data) {

        Command command = CommandFactory.getInstance().createCommand(addNewCommand);
        Message request = new Message();
        request.add(key, data);
        Message response = command.execute(request);

        String exceptionMessage = (String) response.getByKey("ex");
        if (exceptionMessage != null) {
            ShowAlert.showErrorAlert(exceptionMessage);
            return false;
        } else {
            return true;
        }
    }
}
