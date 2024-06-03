package org.example;
import java.io.BufferedReader;
import java.io.*;
import java.net.*;

public class Client {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    public Client(String address, int port) throws IOException {
        socket = new Socket(address, port);
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public void start() {
        try (BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in))) {
            String userInput;
            while ((userInput = stdIn.readLine()) != null) {
                /*out.println(userInput);
                System.out.println("Server response: " + in.readLine());
                if (userInput.equals("exit")) {
                    break;
                }*/
                out.println(userInput);
                String response;
                System.out.println("Server response: ");
                while ((response = in.readLine()) != null) {
                    System.out.println(response);
                    if (response.isEmpty()) {
                        break; // Se termină de citit răspunsul de la server
                    }
                }
                if (userInput.equals("exit")) {
                    break;
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

    public static void main(String[] args) {
        try {
            Client client = new Client("localhost", 1234);
            client.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
