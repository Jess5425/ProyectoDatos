package com.example.prueba;



import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class MultiClientServer {
    private static final int PORT = 12345;
    private static List<Socket> clientSockets = new ArrayList<>();

    private static volatile boolean serverRunning = true;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server started. Listening on port " + PORT);

            while (serverRunning) {
                Socket clientSocket = serverSocket.accept();
                clientSockets.add(clientSocket);

                // Handle each client in a separate thread or task
                // You can use a thread pool for better management
                new Thread(new ClientHandler(clientSocket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class ClientHandler implements Runnable {
        private final Socket socket;
        private PrintWriter out;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);

                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    // Handle client input and broadcast to other clients
                    broadcast(inputLine);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    socket.close();
                    clientSockets.remove(socket);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        private void broadcast(String message) {
            for (Socket client : clientSockets) {
                if (client != socket) {
                    try {
                        PrintWriter clientOut = new PrintWriter(client.getOutputStream(), true);
                        clientOut.println(message);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
