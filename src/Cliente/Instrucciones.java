package Cliente;

/***
 * Clase que se utiliza para enviar instrucciones al servidor
 */
public class Instrucciones {
    public String instruccion;
    public String datos;

    /***
     * Metodo constructor
     * @param instruccion instruccion que se envia al servidor
     * @param datos datos adicionales para ejecutar la accion
     */
    public Instrucciones(String instruccion, String datos) {
        this.instruccion = instruccion;
        this.datos = datos;
    }
}
