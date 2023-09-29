package Servidor.Malla.Casillas;

import Servidor.Jugadores.NodoUsuario;
import Servidor.Jugadores.Usuario;

/**
 * Metodo donde se almacenan los nodos a manera de lista
 */

public class Fila {
    public NodoUsuario front;
    public NodoUsuario rear;

    /**
     * Inserta un usuario al final de la lista
     * @param data usuario a ingresar
     */
    public void insertarUsuario(Usuario data) {
        NodoUsuario newNode = new NodoUsuario(data);

        if (front==null) {
            front = newNode;
            rear = newNode;
        } else {
            rear.next = newNode;
            rear = newNode;
        }
    }

}
