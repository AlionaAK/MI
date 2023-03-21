package com.example.domain.connector;

public interface ConnectionListener {


    void connectionCreated(Connection c);

    void connectionClose(Connection c);

    void connectionException(Exception ex);
}
