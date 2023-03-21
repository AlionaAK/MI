package com.example.domain.connector;


import com.example.domain.message.Message;

public interface Connection {

    void send(Message message);

    Message makeDialog(Message message);
    void close();
}
