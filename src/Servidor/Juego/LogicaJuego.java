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
}