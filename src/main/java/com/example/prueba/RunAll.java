package com.example.prueba;

import javafx.stage.Stage;

import java.io.IOException;
import java.net.Socket;

public class RunAll {
    public static void main(String[] args) {
        MultiClientServer.main(args);

        try {
            Socket socket1 = new Socket("localhost", 12345);
            Socket socket2 = new Socket("localhost", 12345);
            Socket socket3 = new Socket("localhost", 12345);

            VentanaHandler ventanaHandler1 = new VentanaHandler(socket1);
            ventanaHandler1.start(new Stage());

            VentanaHandler ventanaHandler2 = new VentanaHandler(socket2);
            ventanaHandler2.start(new Stage());

            VentanaHandler ventanaHandler3 = new VentanaHandler(socket3);
            ventanaHandler3.start(new Stage());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
