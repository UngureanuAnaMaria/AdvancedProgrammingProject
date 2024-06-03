package com.nictas.eclipselink.postgresql.server;

import com.nictas.eclipselink.postgresql.exec.ExecuteProblem;
import com.nictas.eclipselink.postgresql.exec.ExecuteRandomProblem;

import java.net.*;
import java.io.*;

public class ClientThread extends Thread {
    private Socket socket;
    private Server server;

    public ClientThread(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
    }

    @Override
    public void run() {
        try (
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        ) {
            String request;
            while ((request = in.readLine()) != null) {
                if (request.equals("stop")) {
                    server.stop();
                    out.println("Server stopped");
                    break;
                } else if(request.equals("execute problem")) {
                    String output = ExecuteProblem.execute();
                    //System.out.println(output);
                    out.println(output + "\n");
                } else if(request.equals("execute random problem")) {
                    String output = ExecuteRandomProblem.execute();
                    //System.out.println(output);
                    out.println(output + "\n");
                } else {
                    out.println("Server received the request: " + request);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}