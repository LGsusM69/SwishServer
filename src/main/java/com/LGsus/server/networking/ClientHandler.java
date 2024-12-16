package com.LGsus.server.networking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler implements Runnable{
    private Server server;
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private String nickName;

    public ClientHandler(Socket socket, Server server, BufferedReader in, String nickName) {
        this.socket = socket;
        this.server = server;
        this.in = in;
        this.nickName = nickName;
    }

    @Override
    public void run() {
        try {
            //in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            String message;
            while((message = in.readLine()) != null) {
                System.out.println("Received: " + message);
                if(message.equals("/close")) {
                    out.println("/close");
                    break;
                }
                server.broadcast(message);
            }
        } catch(IOException e) {
            System.out.println("Client disconnected: " + e.getMessage());
        } finally {
            server.removeClient(this);
            try {
                in.close();
                out.close();
                socket.close();
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void sendMessage (String message) {
        out.println(nickName + ": " + message);
    }
    public String getNickName() {return nickName;}
}
