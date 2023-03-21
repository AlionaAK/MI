package com.example.domain.server;


import java.io.*;
import java.net.Socket;

import com.example.domain.manager.Manager;
import com.example.domain.message.Message;


public class ConnectionListenerImpl implements Connection, Runnable {

    private boolean needToRun = true;
    //
    private Socket socket;

    private InputStream in;
    private OutputStream out;

    public ConnectionListenerImpl(Socket socket, ConnectionListener connectionListener) throws Exception {
        this.socket = socket;
        this.in = socket.getInputStream();
        this.out = socket.getOutputStream();

        Thread t = new Thread(this);
        t.setPriority(Thread.MIN_PRIORITY);
        t.start();
    }

    @Override
    public void send(Message message) {
        try {
            System.out.println("Message sent");
            ObjectOutputStream objOut = new ObjectOutputStream(out);
            objOut.writeObject(message); // отправка объекта
            objOut.flush(); // "заталкивание" объекта
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    @Override
    public void close() {
        try {
            this.needToRun = false;
            this.socket.close();
            this.in.close();
            this.out.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            System.out.println("Соединение закрыто!");
        }
    }


    //основная функция работы сервера
    @Override
    public void run() {

        while (needToRun) {
            try {
                ObjectInputStream objIn = new ObjectInputStream(in); //reading data

                Message msq = (Message) objIn.readObject(); //waiting for object

                sendMessage(msq);
            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        }
    }


    private synchronized void sendMessage(Message message) {
        Manager manager = new Manager();
        System.out.println(message.getCommand());

        Message response = manager.doAction(message);
        send(response);
    }
}