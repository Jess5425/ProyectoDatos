package Servidor.Conexion;

import Servidor.Juego.LogicaJuego;
import Servidor.Jugadores.Cola;
import Servidor.Jugadores.Usuario;
import Servidor.Instrucciones;
import com.fasterxml.jackson.databind.ObjectMapper;

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

        final int PORT = 5000;

        try {
            serverSocket = new ServerSocket(PORT);

            while (true){

                clientSocket = serverSocket.accept();

                in = new DataInputStream(clientSocket.getInputStream());
                out = new DataOutputStream(clientSocket.getOutputStream());

                String[] message = in.readUTF().split("/e/");

                ObjectMapper mapperTemp = new ObjectMapper();
                Instrucciones instrucciones = mapperTemp.readValue(message[0], Instrucciones.class);

                switch (instrucciones.instruccion){
                    case "registrarJugador":{
                        if (!juegoIniciado){
                            ObjectMapper mapper = new ObjectMapper();
                            Usuario user = mapper.readValue(message[1], Usuario.class);

                            if (jugadores.existeNombre(user.nombre)){
                                out.writeUTF("Registrado Fallido");
                            }
                            else{

                                jugadores.enqueue(user);

                                out.writeUTF("Registrado Exitosamente");
                            }
                        }
                        else{
                            out.writeUTF("Juego Iniciado");
                        }
                        break;
                    }
                    case "iniciarJuego":{
                        if (juegoIniciado == false){
                            if(jugadores.cantidadJugadores()>=2){
                                juegoIniciado = true;
                                juego = new LogicaJuego(jugadores);
                                out.writeUTF("Juego Iniciado");
                            }
                            else{
                                out.writeUTF("Juego No Iniciado");
                            }
                        }
                        else{
                            out.writeUTF("Juego Ya Iniciado");
                        }
                        break;

                    }
                    case "pintarVentana":{
                        if (juegoIniciado){
                            ObjectMapper mapper = new ObjectMapper();
                            String response = mapper.writeValueAsString(juego.malla);

                            out.writeUTF(response);
                            break;

                        }
                        out.writeUTF("");
                        break;
                    }
                    case "seleccionarLinea":{
                        if (juegoIniciado){
                            ObjectMapper mapper = new ObjectMapper();
                            Usuario user = mapper.readValue(message[1], Usuario.class);

                            String[] coordenadas = instrucciones.datos.split(",");

                            if (juego.jugarTurno(user,Integer.parseInt(coordenadas[0]),Integer.parseInt(coordenadas[1]))){
                                out.writeUTF("Turno Jugado");
                            }
                            else{
                                out.writeUTF("Turno No Jugado");
                            }

                        }
                        else{
                            out.writeUTF("Juego No Iniciado");
                        }
                        break;

                    }
                }







            }



        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}


