package com.LGsus.server.controllers;

import com.LGsus.server.networking.Server;

import java.io.IOException;
import java.net.BindException;
import java.util.concurrent.TimeUnit;

public class ServerController {
    private Server server;

    public ServerController() {
        this.server = new Server(12345);
    }

    public String startServer(int port) {
        try {
            server = new Server(port);
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
}
