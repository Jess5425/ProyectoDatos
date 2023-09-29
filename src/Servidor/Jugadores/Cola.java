package Servidor.Jugadores;

public class Cola {
    private NodoUsuario front;
    private NodoUsuario rear;

    public Cola() {
        this.front = null;
        this.rear = null;
    }

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

    public int cantidadJugadores(){
        NodoUsuario current = front;
        int contador = 0;
        while (current != null) {
            contador += 1;
            current = current.next;
        }
        return contador;
    }

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

    public boolean isEmpty() {
        return front == null;
    }

    public void display() {
        NodoUsuario current = front;
        while (current != null) {
            System.out.print(current.data.nombre + " -> ");
            current = current.next;
        }
        System.out.println("null");
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