package com.example.domain.command;

import com.example.domain.message.Message;

import java.io.Serializable;


public interface Command extends Serializable {

    Message execute(Message request);

}
