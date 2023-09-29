package Servidor.Jugadores;

public class NodoUsuario {
    public Usuario data;
    public NodoUsuario next;

    public NodoUsuario(Usuario data) {
        this.data = data;
        this.next = null;
    }
}
