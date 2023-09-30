package Cliente.Conexion;


import Cliente.Malla.Lineas.Malla;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import Cliente.Usuario;
import Cliente.Instrucciones;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

/***
 * Clase encargada de cominucarse con el servidor
 */
public class Cliente {
    /***
     * Funcion encargada de registrar Jugadores
     * @param user Usuario a registrar
     * @return respuesta del servidor
     */
    public static String registrarJugador(Usuario user) {

        final String HOST = "localhost";

        DataInputStream in;
        DataOutputStream out;

        final int PORT = user.puerto;

        ObjectMapper mapper = new ObjectMapper();

        try {
            //Transformar de usuarios a JSON
            String json = mapper.writeValueAsString(user);

            Socket clientSocket = new Socket(HOST, PORT);

            in = new DataInputStream(clientSocket.getInputStream());
            out = new DataOutputStream(clientSocket.getOutputStream());

            Instrucciones instrucciones = new Instrucciones("registrarJugador","");
            String instruccion = mapper.writeValueAsString(instrucciones);

            out.writeUTF(instruccion+"/e/" + json);

            String message = in.readUTF();

            clientSocket.close();
            return message;


        } catch (JsonProcessingException | UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /***
     *Solicita al servidor iniciar el juego
     * @return respuesta del servidor
     */
    public static String iniciarJuego() {

        final String HOST = "localhost";

        DataInputStream in;
        DataOutputStream out;

        final int PORT = 5000;

        ObjectMapper mapper = new ObjectMapper();

        try {
            Socket clientSocket = new Socket(HOST, PORT);

            in = new DataInputStream(clientSocket.getInputStream());
            out = new DataOutputStream(clientSocket.getOutputStream());

            Instrucciones instrucciones = new Instrucciones("iniciarJuego","");
            String instruccion = mapper.writeValueAsString(instrucciones);

            out.writeUTF(instruccion);

            String message = in.readUTF();

            clientSocket.close();
            return message;


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /***
     * Pide los datos necesarios para cargar en pantalla la malla del tablero
     * @return Malla con los datos de la partida
     */
    public static Malla pintarVentana() {

        final String HOST = "localhost";

        DataInputStream in;
        DataOutputStream out;

        final int PORT = 5000;

        ObjectMapper mapper = new ObjectMapper();

        try {
            Socket clientSocket = new Socket(HOST, PORT);

            in = new DataInputStream(clientSocket.getInputStream());
            out = new DataOutputStream(clientSocket.getOutputStream());

            Instrucciones instrucciones = new Instrucciones("pintarVentana","");
            String instruccion = mapper.writeValueAsString(instrucciones);

            out.writeUTF(instruccion);

            String message = in.readUTF();

            clientSocket.close();

            if (message.equals("")){
                return null;

            }
            else {
                Malla malla = mapper.readValue(message, Malla.class);

                return malla;
            }




        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /***
     * Pide al servidor determinar si ya hay un ganador en la partida
     * @return si hay ganador devuelve al Usuario ganador, de caso contrario retorna null
     */
    public static Usuario ganador() {

        final String HOST = "localhost";

        DataInputStream in;
        DataOutputStream out;

        final int PORT = 5000;

        ObjectMapper mapper = new ObjectMapper();

        try {
            Socket clientSocket = new Socket(HOST, PORT);

            in = new DataInputStream(clientSocket.getInputStream());
            out = new DataOutputStream(clientSocket.getOutputStream());

            Instrucciones instrucciones = new Instrucciones("ganador","");
            String instruccion = mapper.writeValueAsString(instrucciones);

            out.writeUTF(instruccion);

            String message = in.readUTF();

            clientSocket.close();

            if (message.equals("")){
                return null;

            }
            else {
                Usuario usuario = mapper.readValue(message, Usuario.class);

                return usuario;
            }




        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /***
     * Envia los datos necesarios al servidor para procesar la linea seleccionada
     * @param user usuario que selecciono la linea
     * @param x coordenadas en el eje X del mouse
     * @param y coordenada en el eje Y del mouse
     */
    public static void seleccionarLinea(Usuario user, int x, int y) {

        final String HOST = "localhost";

        DataInputStream in;
        DataOutputStream out;

        final int PORT = 5000;

        ObjectMapper mapper = new ObjectMapper();

        try {
            //Transformar de usuarios a JSON
            String json = mapper.writeValueAsString(user);

            Socket clientSocket = new Socket(HOST, PORT);

            in = new DataInputStream(clientSocket.getInputStream());
            out = new DataOutputStream(clientSocket.getOutputStream());

            Instrucciones instrucciones = new Instrucciones("seleccionarLinea",String.valueOf(x)+","+String.valueOf(y));
            String instruccion = mapper.writeValueAsString(instrucciones);

            out.writeUTF(instruccion+"/e/"+json);

            String message = in.readUTF();

            clientSocket.close();


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
