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

    private void iniciarMalla() {
        int y = 1;
        for (int i = 0; i < (CASILLAS_JUEGO * 2) + 1; i++) {
            List<Integer> lista1 = new ArrayList<>();
            List<List<Integer>> lista2 = new ArrayList<>();
            if (i % 2 == 0) {
                for (int j = 0; j < CASILLAS_JUEGO; j++) {
                    lista1.add(0);
                    List<Integer> listaTemp = new ArrayList<>();
                    listaTemp.add((j + 1) * 30 + 15);
                    listaTemp.add((y) * 30);
                    lista2.add(listaTemp);

                }
            }
            if (i % 2 != 0) {
                for (int j = 0; j < CASILLAS_JUEGO + 1; j++) {
                    lista1.add(0);

                    List<Integer> listaTemp = new ArrayList<>();
                    listaTemp.add((j + 1) * 30);
                    listaTemp.add((y) * 30 + 15);
                    lista2.add(listaTemp);
                }
                y += 1;
            }
            malla.add(lista1);
            coordenadas.add(lista2);
        }
    }

    private void iniciarCasillas() {
        for (int i = 0; i < CASILLAS_JUEGO; i++) {
            List<Usuario> lista1 = new ArrayList<>();
            for (int j = 0; j < CASILLAS_JUEGO; j++) {
                lista1.add(null);
            }
            casillasUsuarios.add(lista1);

        }

    }
    private List<Integer> calcularPuntoCercano(double x, double y) {
        List<Integer> res = new ArrayList<>();
        double distancia = -1;

        for (int i = 0; i < coordenadas.size(); i++) {
            for (int j = 0; j < coordenadas.get(i).size(); j++) {
                double tempX = coordenadas.get(i).get(j).get(0);
                double tempY = coordenadas.get(i).get(j).get(1);


                double tempDistancia = Math.sqrt(Math.pow((tempX - x), 2) + Math.pow((tempY - y), 2));

                if (distancia == -1 || distancia > tempDistancia) {
                    distancia = tempDistancia;
                    res = new ArrayList<>();
                    res.add(j);
                    res.add(i);
                }

            }

        }

        return res;
    }

    public boolean jugarTurno(Usuario jugador, double x, double y) {

        if (ordenJugadores.getFront() == null) {
            this.ordenJugadores.setFront(jugadores.getFront());
            this.ordenJugadores.setRear(jugadores.getRear());
        }

        if (jugador.nombre.equals(ordenJugadores.getFront().data.nombre)) {


            List<Integer> coordenadas = calcularPuntoCercano(x, y);

            if (malla.get(coordenadas.get(1)).get(coordenadas.get(0)) == 1) {
                return false;
            }
            ordenJugadores.dequeue();

            List<List<Integer>> mallaTemp = new ArrayList<>();

            for (int i = 0; i < (CASILLAS_JUEGO * 2) + 1; i++) {
                List<Integer> lista1 = new ArrayList<>();
                if (i % 2 == 0) {
                    for (int j = 0; j < CASILLAS_JUEGO; j++) {
                        if (coordenadas.get(1) == i && coordenadas.get(0) == j) {
                            lista1.add(1);
                            casillaEncerrada(jugador, j, i);
                        } else {
                            lista1.add(malla.get(i).get(j));
                        }

                    }
                }
                if (i % 2 == 1) {
                    for (int j = 0; j < CASILLAS_JUEGO + 1; j++) {
                        if (coordenadas.get(1) == i && coordenadas.get(0) == j) {
                            lista1.add(1);
                            casillaEncerrada(jugador, j, i);
                        } else {
                            lista1.add(malla.get(i).get(j));
                        }
                    }
                }
                mallaTemp.add(lista1);
            }
            malla = mallaTemp;


            return true;
        }
        return false;

    }

    public boolean casillaEncerrada(Usuario jugador, int x, int y) {
        boolean var = false;

        if (y % 2 == 0) {
            if (y - 2 >= 0 && malla.get(y - 2).get(x) == 1 && malla.get(y - 1).get(x) == 1 && malla.get(y - 1).get(x + 1) == 1) {
                List<List<Usuario>> casillasUsuariosTemp = new ArrayList<>();
                for (int i = 0; i < CASILLAS_JUEGO; i++) {
                    List<Usuario> lista1 = new ArrayList<>();
                    for (int j = 0; j < CASILLAS_JUEGO; j++) {
                        if (i == (y / 2) - 1 && j == x) {
                            lista1.add(jugador);
                            System.out.println(jugador.nombre);
                            System.out.println(j);//x
                            System.out.println(i);//y
                        } else {
                            lista1.add(casillasUsuarios.get(i).get(j));
                        }

                    }
                    casillasUsuariosTemp.add(lista1);
                }
                this.casillasUsuarios = casillasUsuariosTemp;
            }
            if (y + 2 < CASILLAS_JUEGO * 2 + 1 && malla.get(y + 2).get(x) == 1 && malla.get(y + 1).get(x) == 1 && malla.get(y + 1).get(x + 1) == 1) {
                List<List<Usuario>> casillasUsuariosTemp = new ArrayList<>();
                for (int i = 0; i < CASILLAS_JUEGO; i++) {
                    List<Usuario> lista1 = new ArrayList<>();
                    for (int j = 0; j < CASILLAS_JUEGO; j++) {
                        if (i == (y / 2) && j == x) {
                            lista1.add(jugador);
                            System.out.println(jugador.nombre);
                            System.out.println(j);//x
                            System.out.println(i);//y
                        } else {
                            lista1.add(casillasUsuarios.get(i).get(j));
                        }

                    }
                    casillasUsuariosTemp.add(lista1);
                }
                this.casillasUsuarios = casillasUsuariosTemp;
            }
        } else {
            if (x - 1 >= 0 && malla.get(y).get(x - 1) == 1 && malla.get(y - 1).get(x - 1) == 1 && malla.get(y + 1).get(x - 1) == 1) {
                List<List<Usuario>> casillasUsuariosTemp = new ArrayList<>();
                for (int i = 0; i < CASILLAS_JUEGO; i++) {
                    List<Usuario> lista1 = new ArrayList<>();
                    for (int j = 0; j < CASILLAS_JUEGO; j++) {
                        if (i == (int)(y / 2) && j == x - 1) {
                            lista1.add(jugador);
                            System.out.println(jugador.nombre);
                            System.out.println(j);//x
                            System.out.println(i);//y
                        } else {
                            lista1.add(casillasUsuarios.get(i).get(j));
                        }

                    }
                    casillasUsuariosTemp.add(lista1);
                }
            }
            if (x + 1 < CASILLAS_JUEGO+1 && malla.get(y).get(x + 1) == 1 && malla.get(y - 1).get(x) == 1 && malla.get(y + 1).get(x) == 1) {
                List<List<Usuario>> casillasUsuariosTemp = new ArrayList<>();
                for (int i = 0; i < CASILLAS_JUEGO; i++) {
                    List<Usuario> lista1 = new ArrayList<>();
                    for (int j = 0; j < CASILLAS_JUEGO; j++) {
                        if (i == (int)(y / 2) && j == x) {
                            lista1.add(jugador);
                            System.out.println(jugador.nombre);
                            System.out.println(j);//x
                            System.out.println(i);//y
                        } else {
                            lista1.add(casillasUsuarios.get(i).get(j));
                        }

                    }
                    casillasUsuariosTemp.add(lista1);
                }
            }
        }

        return var;
    }
}


