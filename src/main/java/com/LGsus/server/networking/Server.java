package com.LGsus.server.networking;

import com.LGsus.server.controllers.ServerController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server implements Runnable{
    private int port;
    private ServerSocket serverSocket;
    private ArrayList<ClientHandler> clients = new ArrayList<>();
    private Exception exc;
    private ServerController controller;

    public Server(int port, ServerController serverController) {
        this.port = port;
        this.controller = serverController;
    }

    @Override
    public void run() {
        System.out.println("Starting server on port: " + port);
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Accepting connections on port: " + port);
            System.out.println("deberia de ser: " + getExc());
            while(serverSocket != null) {
                Socket socket = serverSocket.accept();
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String nickName = in.readLine();
                String ipAdress = socket.getRemoteSocketAddress().toString();
                ClientHandler clientHandler = new ClientHandler(socket, this, in, nickName);
                clients.add(clientHandler);
                new Thread(clientHandler).start();
                controller.addClient(nickName, ipAdress);
                System.out.println("Connection accepted");
            }
        } catch(Exception e) {
            System.out.println("Server error: " + e.getMessage());
            exc = e;
            System.out.println("deberia de ser: " + getExc());
        }

    }
    public void broadcast(String message) {
        for(ClientHandler client : clients) {
            client.sendMessage(message);
        }
    }
    public void removeClient(ClientHandler client) {
        String nickName = client.getNickName();
        controller.removeClient(nickName);
        clients.remove(client);
    }
    public void stopServer() {
        try {
            serverSocket.close();

        } catch(IOException e) {
            System.out.println("Server error: " + e.getMessage());
        }
    }
    public Exception getExc() {
        return exc;
    }
}
