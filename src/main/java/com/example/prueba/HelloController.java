package com.example.prueba;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class HelloController {
    @FXML
    private Label welcomeText;

    private Socket socket;

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    @FXML
    protected void onHelloButtonClick() {
        String message = "Welcome to JavaFX Application!";
        // Send the message to the server
        try {
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Handle messages received from the server and update the view
    public void handleMessage(String message) {
        Platform.runLater(() -> {
            welcomeText.setText(message);
        });
    }

    // Method to stop the server
    public void stopServer() {
        String stopCommand = "STOP_SERVER";
        try {
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println(stopCommand);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}