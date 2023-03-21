package com.example.domain.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

public class Server implements ConnectionListener {

    public static int PORT;
    private Set<Connection> connections = new HashSet<>();

    private ServerSocket serverSocket;

    public Server() {
        try {
            this.serverSocket = new ServerSocket(PORT);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }


    public void startServer() {
        System.out.println("Server  started..");
        while (true) {
            try {
                Socket socket = serverSocket.accept(); //wait for client socket
                connectionCreated(new ConnectionListenerImpl(socket, this)); //created thread
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
    }


    @Override
    public void connectionCreated(Connection c) {
        System.out.println("Connection added." + c.toString());
        connections.add(c);
    }

    @Override
    public void connectionClose(Connection c) {
        System.out.println("Connection closed");
        connections.remove(c);
        c.close();
    }

    @Override
    public void connectionException(Exception ex) {
        throw new RuntimeException(ex);
    }
}


