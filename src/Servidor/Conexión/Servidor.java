import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Servidor {

    private Cola jugadores;
    private boolean juegoIniciado;
    private LogicaJuego juego;

    public Servidor() {

        jugadores = new Cola();
        juegoIniciado = false;

        iniciarServidor();

    }
    private void iniciarServidor(){
        ServerSocket serverSocket = null;
        Socket clientSocket = null;

        DataInputStream in;
        DataOutputStream out;

        final int PORT=5000;

}