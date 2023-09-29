package Cliente;

/***
 * Clase Usuario
 */
public class Usuario {
    public int puerto;
    public String nombre;
    public Usuario(){}

    /***
     * Metodo constructor
     * @param p puerto
     * @param n nombre del usuario
     */
    public Usuario(int p, String n){
        this.puerto=p;
        this.nombre=n;
    }
}
