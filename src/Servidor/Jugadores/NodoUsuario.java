package Servidor.Jugadores;

/**
 * Nodo donde se almacenan los usuarios
 */
public class NodoUsuario {
    public Usuario data;
    public NodoUsuario next;

    /**
     * Metodo constructor
     * @param data
     */
    public NodoUsuario(Usuario data) {
        this.data = data;
        this.next = null;
    }
}
