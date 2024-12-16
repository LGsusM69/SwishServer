package com.LGsus.server.controllers;

import com.LGsus.server.networking.Server;
import com.LGsus.server.views.ServerGUI;

import java.io.IOException;
import java.net.BindException;
import java.util.concurrent.TimeUnit;

public class ServerController {
    private Server server;
    private ServerGUI gui;

    public ServerController(ServerGUI gui) {
        this.gui = gui;
    }

    public String startServer(int port) {
        try {
            server = new Server(port, this);
            Thread serverThread = new Thread(server);
            serverThread.start();
            TimeUnit.MILLISECONDS.sleep(500);
            Exception exc = server.getExc();
            if(exc == null) return "success";
            else throw exc;
        } catch(Exception e) {
            String error = "Server error: " + e.getMessage();
            return error;
        }
    }
    public void stopServer() {
        System.out.println("stopping server...");
        server.stopServer();
    }
    public void addClient(String nickName, String ipAdress) {
        gui.addClient(nickName, ipAdress);
    }
    public void removeClient(String nickName) {
        gui.removeClient(nickName);
    }
}
