package Servidor.Malla.Lineas;
/***
 * Almacena la informacion de las lineas
 */
public class Linea {

    public boolean seleccionada;
    public int x;
    public int y;


    /**
     * Metodo constructor
     * @param x coordenada x
     * @param y coordenada y
     */
    public Linea(int x, int y) {
        this.seleccionada = false;
        this.x = x;
        this.y = y;
    }
}
