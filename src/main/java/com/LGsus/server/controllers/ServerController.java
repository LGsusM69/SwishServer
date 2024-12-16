package com.LGsus.server.controllers;

import com.LGsus.server.networking.Server;

import java.io.IOException;

public class ServerController {
    private Server server;

    public ServerController() {
        this.server = new Server(12345);
    }

    public void startServer() {
        try {
            server = new Server(12345);
            Thread serverThread = new Thread(server);
            serverThread.start();
        } catch(Exception e) {
            System.out.println("ServerController error: " + e.getMessage());
        }
    }
    public void stopServer() {
        System.out.println("stopping server...");
        server.stopServer();
    }
}
