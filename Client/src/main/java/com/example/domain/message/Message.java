package com.example.domain.message;


import com.example.domain.command.Commands;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Message<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    private Map<String, T> request;
    private Commands command;

    public Message() {
        this.request = new HashMap<>();
    }

    public Message(Map<String, T> request, Commands command) {
        this.request = request;
        this.command = command;
    }


    public Map<String, T> getRequest() {
        return request;
    }

    public T getByKey(String key) {
        return this.request.get(key);
    }

    public Commands getCommand() {
        return command;
    }

    ///
    public void setCommand(Commands command) {
        this.command = command;
    }

    public void add(String key, T object) {
        this.request.put(key, object);
    }

}

