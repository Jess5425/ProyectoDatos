package Servidor.Jugadores;

/***
 * Clase donde se almacenan los jugadores
 */
public class Cola {
    private NodoUsuario front;
    private NodoUsuario rear;

    /***
     * Metodo constructor
     */
    public Cola() {
        this.front = null;
        this.rear = null;
    }

    /**
     * Verifica si existe un nombre en la lista
     * @param nombre nombre a buscar
     * @return true si se encuentra, false si no
     */
    public boolean existeNombre(String nombre){
        NodoUsuario current = front;
        while (current != null) {
            if (current.data.nombre.equals(nombre)){
                return true;
            }
            current = current.next;
        }
        return false;
    }

    /***
     * Calcula la cantida de jugadores en la partida
     * @return cantida de jugadores
     */
    public int cantidadJugadores(){
        NodoUsuario current = front;
        int contador = 0;
        while (current != null) {
            contador += 1;
            current = current.next;
        }
        return contador;
    }

    /**
     * Agrega un jugador a la lista
     * @param data
     */
    public void enqueue(Usuario data) {
        NodoUsuario newNode = new NodoUsuario(data);

        if (isEmpty()) {
            front = newNode;
            rear = newNode;
        } else {
            rear.next = newNode;
            rear = newNode;
        }
    }

    /**
     * Elimina el primer jugador de la lista
     * @return jugador eliminado
     */
    public Usuario dequeue() {
        if (isEmpty()) {
            throw new IllegalStateException("Queue is empty");
        }

        Usuario data = front.data;
        front = front.next;

        if (front == null) {
            rear = null;
        }

        return data;
    }

    /**
     * verifica si la lista esta vacia
     * @return
     */
    public boolean isEmpty() {
        return front == null;
    }
    public NodoUsuario getFront() {
        return front;
    }

    public void setFront(NodoUsuario front) {
        this.front = front;
    }

    public NodoUsuario getRear() {
        return rear;
    }

    public void setRear(NodoUsuario rear) {
        this.rear = rear;
    }
}
