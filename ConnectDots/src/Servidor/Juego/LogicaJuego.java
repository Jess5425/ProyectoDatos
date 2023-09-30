package Servidor.Juego;

import Servidor.Jugadores.Cola;
import Servidor.Jugadores.Usuario;
import Servidor.Malla.Lineas.Filas;
import Servidor.Malla.Lineas.Linea;
import Servidor.Malla.Lineas.Malla;
import Servidor.Malla.Lineas.NodoLinea;

/***
 * Clase donde se ejecuta toda la logica del juego
 */
public class LogicaJuego {

    public Malla malla;

    Cola ordenJugadores;
    Cola jugadores;
    public final static int CASILLAS_JUEGO = 3;

    /***
     * Metodo constructor
     * @param jugadores cola de jugadores que participaran en la partida
     */
    public LogicaJuego(Cola jugadores) {
        this.jugadores = jugadores;
        this.ordenJugadores = new Cola();
        this.ordenJugadores.setFront(jugadores.getFront());
        this.ordenJugadores.setRear(jugadores.getRear());
        this.malla = new Malla(CASILLAS_JUEGO);

    }

    /***
     * Calcula la linea mas cercana a un punto
     * @param x coordenada en x del punto
     * @param y coordenada en y del punto
     * @return Linea que se encuentra mas cerca de este
     */
    private Linea calcularPuntoCercano(double x, double y) {
        Linea res = null;
        double distancia = -1;

        Filas tempFilas = malla.getFront();

        while (tempFilas != null) {
            NodoLinea tempLineas = tempFilas.getData().getFront();
            while (tempLineas != null) {
                double tempDistancia = Math.sqrt(Math.pow((tempLineas.getLinea().x - x), 2) + Math.pow((tempLineas.getLinea().y - y), 2));

                if (distancia == -1 || distancia > tempDistancia) {
                    distancia = tempDistancia;
                    res = tempLineas.getLinea();
                }

                tempLineas = tempLineas.getNext();
            }
            tempFilas = tempFilas.getNext();
        }
        return res;
    }

    /***
     * Juega un turno con la linea seleccionada y el jugador correspondiente
     * @param jugador jugador que selecciono la linea
     * @param x coordenada en x del punto
     * @param y coordenada en y del punto
     * @return
     */
    public boolean jugarTurno(Usuario jugador, double x, double y) {
        //verifica si en la lista del orden de jugadores aun hay jugadores disponibles
        if (ordenJugadores.getFront() == null) {
            this.ordenJugadores.setFront(jugadores.getFront());
            this.ordenJugadores.setRear(jugadores.getRear());
        }
        //Verifica que si sea el turno del jugador
        if (jugador.nombre.equals(ordenJugadores.getFront().data.nombre)) {
            Linea coordenadas = calcularPuntoCercano(x, y);

            //Si la linea que se busco se puede seleccionar entonces procesa acepta el turno
            if (malla.seleccionarLinea(coordenadas)) {
                ordenJugadores.dequeue();
                malla.casillaEncerrada(jugador, coordenadas);

                return true;
            }
        }
        return false;

    }
}
