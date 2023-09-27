package Cliente.Conexion;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import Cliente.Usuario;




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

}