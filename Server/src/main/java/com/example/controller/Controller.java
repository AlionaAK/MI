package com.example.controller;


import com.example.controller.listener.ServerContextListener;
import com.example.domain.server.Server;


public class Controller {

    public static void main(String[] args) { 
        ServerContextListener.getInstance().init(); //init db, ports
        //
        Server server = new Server();
        server.startServer();
        //
        ServerContextListener.getInstance().destroy();
    }
}
