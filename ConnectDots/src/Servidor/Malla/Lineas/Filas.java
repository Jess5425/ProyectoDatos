package Servidor.Malla.Lineas;
/**
 * Clase donde se almacenan las filas
 */
public class Filas {

    public Filas next;
    public Lineas data;

    public Filas() {
        this.next = null;
        this.data = null;
    }

    public Filas(Lineas data) {
        this.next = null;
        this.data = data;
    }

    public Filas getNext() {
        return next;
    }

    public void setNext(Filas next) {
        this.next = next;
    }

    public Lineas getData() {
        return data;
    }

    public void setData(Lineas data) {
        this.data = data;
    }
}
