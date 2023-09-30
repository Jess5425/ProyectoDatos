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


/***
 * Servidor que recibe y procesa las peticiones del cliente
 */
public class Servidor {

    private Cola jugadores;
    private boolean juegoIniciado;
    private LogicaJuego juego;

    /***
     * Metodo constructor
     */
    public Servidor() {

        jugadores = new Cola();
        juegoIniciado = false;

        iniciarServidor();

    }

    /***
     * Inicia el servidor y lo mantiene en un ciclo para recibir las peticiones
     */
    private void iniciarServidor(){
        ServerSocket serverSocket = null;
        Socket clientSocket = null;

        DataInputStream in;
        DataOutputStream out;

        final int PORT = 5000;

        try {
            //Abre el socker que comunica con el cliente
            serverSocket = new ServerSocket(PORT);
            //Ciclo infinito donde se esperan las peticiones del cliente
            while (true){

                clientSocket = serverSocket.accept();

                in = new DataInputStream(clientSocket.getInputStream());
                out = new DataOutputStream(clientSocket.getOutputStream());

                String[] message = in.readUTF().split("/e/");

                ObjectMapper mapperTemp = new ObjectMapper();
                Instrucciones instrucciones = mapperTemp.readValue(message[0], Instrucciones.class);
                //Switch que determina que esta solicitando el cliente
                switch (instrucciones.instruccion){
                    //registra un jugador para la partida
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
                    //inicia la partida si se cumplen las condiciones necesarias
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
                    //envia como mensaje un json con la malla del juego
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
                    //procesa la seleccion de una linea
                    case "seleccionarLinea":{
                        if (juegoIniciado){
                            ObjectMapper mapper = new ObjectMapper();
                            Usuario user = mapper.readValue(message[1], Usuario.class);

                            String[] coordenadas = instrucciones.datos.split(",");
                            //verifica si el turno se pudo completar el turno
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
                    //Determina si hay un ganador y envia como respuesta al ganador
                    case "ganador":{
                        if (juegoIniciado){
                            Usuario ganador = juego.malla.ganador(jugadores);
                            if (ganador!=null){
                                ObjectMapper mapper = new ObjectMapper();
                                String response = mapper.writeValueAsString(ganador);

                                out.writeUTF(response);
                                juegoIniciado = false;
                                juego = null;
                                jugadores = new Cola();
                                break;
                            }
                            else{
                                out.writeUTF("");
                                break;
                            }


                        }
                        out.writeUTF("");
                        break;
                    }
                }







            }



        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}


