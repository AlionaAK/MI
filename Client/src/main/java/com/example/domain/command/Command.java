package com.example.domain.command;

import com.example.domain.message.Message;

public interface Command {
    Message execute(Message request);
}
