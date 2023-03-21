package com.example.domain.server;


import com.example.domain.message.Message;


public interface Connection {

    void send(Message message);

    void close();
}
