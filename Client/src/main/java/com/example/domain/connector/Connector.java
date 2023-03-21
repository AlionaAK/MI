package com.example.domain.connector;

import java.net.InetAddress;
import java.net.Socket;

public class Connector implements ConnectionListener {

    private static final Connector instance = new Connector();

    public static Connector getInstance() {
        return instance;
    }

    private Connector() {

    }

    ///////////////////////////////////////////
    private static Connection connection;

    private static int port;

    public static void setPort(int port) {
        Connector.port = port;
    }

    public static int getPort() {
        return port;
    }

    public void doCreateConnection() throws Exception {
        try {
            Socket socket = new Socket(InetAddress.getLocalHost(), port);
            connectionCreated(connection = new ConnectionListenerImpl(socket, this));
        } catch (Exception ex) {
            throw new Exception(ex);
        }
    }

    public boolean isConnectionCreated() {
        return connection != null;
    }

    public static Connection getConnection() {
        return connection;
    }

    @Override
    public void connectionCreated(Connection c) {
        System.out.println("Connection successful!");
    }

    @Override
    public void connectionClose(Connection c) {
        System.out.println("Connection ended");
        c.close();
    }

    @Override
    public void connectionException(Exception ex) {
        ex.printStackTrace();
    }
}
