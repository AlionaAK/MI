package com.example.domain.manager;

import com.example.domain.command.Command;
import com.example.domain.command.factory.CommandFactory;
import com.example.domain.message.Message;


public class Manager {

    public Message doAction(Message message) {
        CommandFactory factory = CommandFactory.getInstance();
        Command command = factory.getCommand(message.getCommand());
        return command.execute(message); // do method from factory
    }
}
