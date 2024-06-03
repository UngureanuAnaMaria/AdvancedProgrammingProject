package com.nictas.eclipselink.postgresql.server;

import java.net.*;
import java.io.*;

public class Server {
    private ServerSocket serverSocket;
    private boolean running = true;
    public Server(int port) throws IOException {
        serverSocket = new ServerSocket(port);
    }


    public void start() {
        System.out.println("Server started...");

        while (running) {
            try {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected...");
                new ClientThread(clientSocket, this).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void stop() {
        running = false;
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            Server server = new Server(1234);
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}