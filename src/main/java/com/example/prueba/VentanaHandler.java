//hola 
package com.example.prueba;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.Socket;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.Socket;

public class VentanaHandler extends Application {
    private Socket socket;

    public VentanaHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(VentanaHandler.class.getResource("View.fxml"));
        Parent root = fxmlLoader.load();

        // Create a socket and set it in the controller
        Controller controller = fxmlLoader.getController();
        controller.setSocket(socket);

        Scene scene = new Scene(root, 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

}
