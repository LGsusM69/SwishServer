package com.LGsus.server.networking;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server implements Runnable{
    private int port;
    private ServerSocket serverSocket;
    private ArrayList<ClientHandler> clients = new ArrayList<>();

    public Server(int port) {
        this.port = port;
    }

    @Override
    public void run() {
        System.out.println("Starting server on port: " + port);
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Accepting connections on port: " + port);
            while(serverSocket != null) {
                Socket socket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(socket, this);
                clients.add(clientHandler);
                new Thread(clientHandler).start();
                System.out.println("Connection accepted");
            }
        } catch(IOException e) {
            System.out.println("Server error: " + e.getMessage());
        }

    }
    public void broadcast(String message) {
        for(ClientHandler client : clients) {
            client.sendMessage(message);
        }
    }
    public void removeClient(ClientHandler client) {
        clients.remove(client);
    }
    public void stopServer() {
        try {
            serverSocket.close();

        } catch(IOException e) {
            System.out.println("Server error: " + e.getMessage());
        }
    }
}
