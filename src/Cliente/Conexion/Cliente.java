package Cliente.Conexion;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import Cliente.Usuario;
import Cliente.Instrucciones;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;

public class Cliente {

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

    public static String iniciarJuego() {

        final String HOST = "localhost";

        DataInputStream in;
        DataOutputStream out;

        final int PORT = 5000;

        ObjectMapper mapper = new ObjectMapper();

        try {
            //Transformar de usuarios a JSON
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

    public static List<List<Integer>> pintarVentana() {

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
                List<List<Integer>> malla = mapper.readValue(message, List.class);

                return malla;
            }




        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String seleccionarLinea(Usuario user, int x, int y) {

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
            return message;


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
