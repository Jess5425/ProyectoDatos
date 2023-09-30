package Servidor.Malla.Casillas;

/***
 * Clase que almacena las casillas de la malla, almacenada como una lista de listas
 * Utilizado para almacenar a quien le pertece la casilla al momento de cerrarse
 */
public class Casillas {
    public Filas front;
    public Filas rear;

    /**
     * Metodo contructor
     * @param size tamanio del tablero
     */
    public Casillas(int size) {

        for (int i = 0; i < size; i++) {
            Fila fila = new Fila();
            for (int j = 0; j < size; j++) {
                fila.insertarUsuario(null);
            }
            insertarFila(fila);
        }

    }

    /**
     * Inserta una fila en la lista
     * @param fila fila a insertar
     */
    public void insertarFila(Fila fila) {
        Filas newNode = new Filas(fila);

        if (front == null) {
            front = newNode;
            rear = newNode;
        } else {
            rear.next = newNode;
            rear = newNode;
        }
    }
}
