package Servidor.Jugadores;

/**
 * Clase usuario
 */
public class Usuario {
    public int puerto;
    public String nombre;

    /**
     * Metodo constructor
     */

    public Usuario(){}

    /**
     * Metodo constructor
     * @param p puerto
     * @param n nombre
     */
    public Usuario(int p, String n){
        this.puerto=p;
        this.nombre=n;
    }
}
