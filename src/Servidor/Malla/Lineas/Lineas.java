package Servidor.Malla.Lineas;
/***
 * Lista de lineas
 */
public class Lineas {

    public NodoLinea front;
    public NodoLinea rear;

    /***
     * Metodo constructor
     */
    public Lineas() {
        this.front = null;
        this.rear = null;
    }

    /**
     * inserta una linea al final de la lista
     * @param x coordenadas en x
     * @param y coordenadas en y
     */
    public void insertarLinea(int x, int y) {
        NodoLinea newNode = new NodoLinea(null,new Linea(x,y));

        if (front==null) {
            front = newNode;
            rear = newNode;
        } else {
            rear.next = newNode;
            rear = newNode;
        }
    }

    public NodoLinea getFront() {
        return front;
    }

    public void setFront(NodoLinea front) {
        this.front = front;
    }

    public NodoLinea getRear() {
        return rear;
    }

    public void setRear(NodoLinea rear) {
        this.rear = rear;
    }
}
