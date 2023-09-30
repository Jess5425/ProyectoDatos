package Servidor.Malla.Lineas;
/***
 * Nodo donde se almacenan las lineas
 */
public class NodoLinea {

    public NodoLinea next;
    public Linea linea;

    /**
     * Metodo constructor
     * @param next
     * @param linea
     */
    public NodoLinea(NodoLinea next, Linea linea) {
        this.next = next;
        this.linea = linea;
    }

    public NodoLinea getNext() {
        return next;
    }

    public void setNext(NodoLinea next) {
        this.next = next;
    }

    public Linea getLinea() {
        return linea;
    }

    public void setLinea(Linea linea) {
        this.linea = linea;
    }
}
