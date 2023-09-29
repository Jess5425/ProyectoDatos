package Servidor.Malla.Casillas;


/**
 * Clase donde se almacenan las filas
 */

public class Filas {
    public Filas next;
    public Fila data;

    /**
     * Metodo constructor
     */
    public Filas() {
        this.next = null;
        this.data = null;
    }

    /**
     * Metodo constructor
     * @param data fila
     */
    public Filas(Fila data) {
        this.next = null;
        this.data = data;
    }
}
