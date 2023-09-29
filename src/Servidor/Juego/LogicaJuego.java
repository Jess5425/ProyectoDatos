package Servidor.Juego;

import Servidor.Jugadores.Cola;
import Servidor.Jugadores.Usuario;

import java.util.ArrayList;
import java.util.List;

public class LogicaJuego {

    public List<List<Integer>> malla;
    public List<List<Usuario>> casillasUsuarios;
    public List<List<List<Integer>>> coordenadas;
    Cola ordenJugadores;
    Cola jugadores;
    int CASILLAS_JUEGO = 10;

    public LogicaJuego(Cola jugadores) {
        this.jugadores = jugadores;
        this.ordenJugadores = new Cola();
        this.ordenJugadores.setFront(jugadores.getFront());
        this.ordenJugadores.setRear(jugadores.getRear());
        this.malla = new ArrayList<>();
        this.coordenadas = new ArrayList<>();
        this.casillasUsuarios = new ArrayList<>();
        iniciarMalla();
        iniciarCasillas();
    }
}