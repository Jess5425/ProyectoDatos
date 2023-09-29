package Servidor.Malla.Lineas;

import Servidor.Juego.LogicaJuego;
import Servidor.Jugadores.Cola;
import Servidor.Jugadores.NodoUsuario;
import Servidor.Jugadores.Usuario;
import Servidor.Malla.Casillas.Casillas;

/**
 * Lista de listas donde se almacenan las lineas de la partida
 */
public class Malla {

    public Filas front;
    public Filas rear;
    public Casillas casillasUsuarios;

    /**
     * Metodo constructor
     * @param size tamanio del tablero
     */
    public Malla(int size) {
        int y = 1;
        for (int i = 0; i < (size * 2) + 1; i++) {
            Lineas lineas = new Lineas();
            if (i % 2 == 0) {
                for (int j = 0; j < size; j++) {
                    lineas.insertarLinea((j + 1) * 30 + 15, (y) * 30);
                }
            }
            if (i % 2 != 0) {
                for (int j = 0; j < size + 1; j++) {
                    lineas.insertarLinea((j + 1) * 30, (y) * 30 + 15);
                }
                y += 1;
            }
            insertarFila(lineas);
        }
        this.casillasUsuarios = new Casillas(size);
    }

    /**
     * Inserta una fila al final de la lista
     * @param filas
     */
    public void insertarFila(Lineas filas) {
        Filas newNode = new Filas(filas);

        if (front == null) {
            front = newNode;
            rear = newNode;
        } else {
            rear.next = newNode;
            rear = newNode;
        }
    }

    /**
     * verifica si la linea ya fue seleccionada, si no la selecciona
     * @param lineaSeleccionada linea a comprobar
     * @return true si se puede seleccionar, si no false
     */
    public boolean seleccionarLinea(Linea lineaSeleccionada) {

        if (!lineaSeleccionada.seleccionada) {

            Filas tempFilas = this.front;

            while (tempFilas != null) {
                NodoLinea tempLinea = tempFilas.data.front;
                while (tempLinea != null) {

                    if (lineaSeleccionada == tempLinea.linea) {
                        tempLinea.linea.seleccionada = true;
                        return true;
                    }

                    tempLinea = tempLinea.next;
                }
                tempFilas = tempFilas.next;
            }


        }
        return false;

    }

    /**
     * Verifica si al seleccionar una linea esta completo una casilla, es decir
     * la encerro
     * @param usuario usuario que selecciono la linea
     * @param lineaSeleccionada linea seleccionada
     */
    public void casillaEncerrada(Usuario usuario, Linea lineaSeleccionada) {
        boolean horizontal = true;

        //Variables utilizada para almacenar las filas anteriores
        Filas tempFilas = this.front;
        Filas tempFilasPrev1 = null;
        Filas tempFilasPrev2 = null;

        //Ciclo que recorre la malla hasta encontrar la que se selecciono
        while (tempFilas != null) {
            NodoLinea tempLineas = tempFilas.data.front;
            int x = 1;
            while (tempLineas != null) {
                //Verifica que la linea se haya encontrado
                if (tempLineas.linea == lineaSeleccionada) {
                    //verifica si es horizontal, ya que se analizan distinto dependiendo de la linea
                    if (horizontal) {
                        //Este if revisa la casilla superior de una linea horizontal
                        //Verifica si la linea 2 espacios antes existe
                        if (tempFilasPrev2 != null) {
                            //Avanza hasta la misma columna que donde esta la linea seleccionada
                            NodoLinea tempLineas2 = tempFilasPrev2.data.front;
                            for (int i = 1; i < x; i++) {
                                tempLineas2 = tempLineas2.next;
                            }
                            //Verifica si esa linea esta seleccionada
                            if (tempLineas2.linea.seleccionada) {
                                //Recorre la fila previa a la seleccionada para revisar las lineas verticales
                                tempLineas2 = tempFilasPrev1.data.front;
                                //Avanza hasta la misma columna que la casilla selecionada
                                for (int i = 1; i < x; i++) {
                                    tempLineas2 = tempLineas2.next;
                                }
                                //Verifica que la lineas verticales esten selecionada
                                if (tempLineas2.linea.seleccionada && tempLineas2.next.linea.seleccionada) {
                                    //Carga la lista de casillas con el Usuario que completo el cuadro
                                    Servidor.Malla.Casillas.Filas filaTemp = casillasUsuarios.front;
                                    for (int i = 1; i < lineaSeleccionada.y / 30 - 1; i++) {
                                        filaTemp = filaTemp.next;
                                    }
                                    NodoUsuario nodoUsuarioTemp = filaTemp.data.front;
                                    for (int j = 1; j < lineaSeleccionada.x / 30; j++) {
                                        nodoUsuarioTemp = nodoUsuarioTemp.next;
                                    }
                                    nodoUsuarioTemp.data = usuario;
                                }
                            }

                        }
                        //Este if revisa la casilla inferior de la linea seleccionada
                        //Verifica que la siguiente fila exista
                        if (tempFilas.next != null) {
                            //Recorre la fila que esta 2 espacios despues de la seleccionada
                            NodoLinea tempLineas2 = tempFilas.next.next.data.front;
                            for (int i = 1; i < x; i++) {
                                tempLineas2 = tempLineas2.next;
                            }
                            //Verifica que la linea se hay seleccionado
                            if (tempLineas2.linea.seleccionada) {
                                //Recorre la fila que esta 1 espacio despues de la seleccionada
                                tempLineas2 = tempFilas.next.data.front;
                                for (int i = 1; i < x; i++) {
                                    tempLineas2 = tempLineas2.next;
                                }
                                //Verifica que las lineas verticales hayan sido seleccionadas
                                if (tempLineas2.linea.seleccionada && tempLineas2.next.linea.seleccionada) {
                                    //Carga la lista de casillas con el Usuario que completo el cuadro
                                    Servidor.Malla.Casillas.Filas filaTemp = casillasUsuarios.front;
                                    for (int i = 1; i < lineaSeleccionada.y / 30; i++) {
                                        filaTemp = filaTemp.next;
                                    }
                                    NodoUsuario nodoUsuarioTemp = filaTemp.data.front;
                                    for (int j = 1; j < lineaSeleccionada.x / 30; j++) {
                                        nodoUsuarioTemp = nodoUsuarioTemp.next;
                                    }
                                    nodoUsuarioTemp.data = usuario;
                                }
                            }


                        }
                    //verifica si es horizontal, ya que se analizan distinto dependiendo de la linea
                    } else {
                        //Este if revisa la casilla izquierda de una linea vertical
                        //Verifica si hay una linea antes de la seleccionada
                        if (x > 1) {
                            NodoLinea tempLineas2 = tempFilas.data.front;
                            //Avanza hasta la una columna antes que donde esta la linea seleccionada, sobre la misma fila
                            for (int i = 1; i < x - 1; i++) {
                                tempLineas2 = tempLineas2.next;
                            }
                            //Verifica si esa linea esta seleccionada
                            if (tempLineas2.linea.seleccionada) {
                                //Recorre la fila previa y siguiente a la seleccionada para revisar las lineas horizontales
                                tempLineas2 = tempFilasPrev1.data.front;
                                NodoLinea tempLineas3 = tempFilas.next.data.front;
                                //Avanza hasta la misma columna que la casilla selecionada
                                for (int i = 1; i < x - 1; i++) {
                                    tempLineas2 = tempLineas2.next;
                                    tempLineas3 = tempLineas3.next;
                                }
                                //Verifica que la lineas horizontales esten selecionada
                                if (tempLineas2.linea.seleccionada && tempLineas3.linea.seleccionada) {
                                    //Carga la lista de casillas con el Usuario que completo el cuadro
                                    Servidor.Malla.Casillas.Filas filaTemp = casillasUsuarios.front;
                                    for (int i = 1; i < lineaSeleccionada.y / 30; i++) {
                                        filaTemp = filaTemp.next;
                                    }
                                    NodoUsuario nodoUsuarioTemp = filaTemp.data.front;
                                    for (int j = 1; j < lineaSeleccionada.x / 30 - 1; j++) {
                                        nodoUsuarioTemp = nodoUsuarioTemp.next;
                                    }
                                    nodoUsuarioTemp.data = usuario;
                                }
                            }
                        }
                        //Este if revisa la casilla derecha de la linea seleccionada
                        //Verifica que la siguiente casilla exista
                        if (x < LogicaJuego.CASILLAS_JUEGO + 1) {
                            NodoLinea tempLineas2 = tempFilas.data.front;
                            //Avanza hasta la una columna despues que donde esta la linea seleccionada, sobre la misma fila
                            for (int i = 1; i < x + 1; i++) {
                                tempLineas2 = tempLineas2.next;
                            }
                            //Verifica si esa linea esta seleccionada
                            if (tempLineas2.linea.seleccionada) {
                                //Recorre la fila previa y siguiente a la seleccionada para revisar las lineas horizontales
                                tempLineas2 = tempFilasPrev1.data.front;
                                NodoLinea tempLineas3 = tempFilas.next.data.front;
                                //Avanza hasta una columna despues que la casilla selecionada
                                for (int i = 1; i < x; i++) {
                                    tempLineas2 = tempLineas2.next;
                                    tempLineas3 = tempLineas3.next;
                                }
                                //Verifica que la lineas horizontales esten selecionada
                                if (tempLineas2.linea.seleccionada && tempLineas3.linea.seleccionada) {
                                    //Carga la lista de casillas con el Usuario que completo el cuadro
                                    Servidor.Malla.Casillas.Filas filaTemp = casillasUsuarios.front;
                                    for (int i = 1; i < lineaSeleccionada.y / 30; i++) {
                                        filaTemp = filaTemp.next;
                                    }
                                    NodoUsuario nodoUsuarioTemp = filaTemp.data.front;
                                    for (int j = 1; j < lineaSeleccionada.x / 30; j++) {
                                        nodoUsuarioTemp = nodoUsuarioTemp.next;
                                    }
                                    nodoUsuarioTemp.data = usuario;
                                }
                            }
                        }
                    }
                }
                x += 1;
                tempLineas = tempLineas.next;
            }
            //Cambia entre horizontal y vertical cada que se avanza de fila
            horizontal = !horizontal;
            //Arrastra las filas previas
            tempFilasPrev2 = tempFilasPrev1;
            tempFilasPrev1 = tempFilas;
            tempFilas = tempFilas.next;
        }
    }

    /**
     * Verifica si hay un ganador
     * @param jugadores cola de jugadores que estan participando
     * @return jugador ganador, null de caso contrario
     */
    public Usuario ganador(Cola jugadores) {
        Usuario res = null;

        int casillas = 0;

        NodoUsuario usuarioRevisar = jugadores.getFront();

        while (usuarioRevisar != null) {
            int casillasTemp = 0;
            Servidor.Malla.Casillas.Filas filasTemp = casillasUsuarios.front;
            while (filasTemp != null) {
                NodoUsuario usuarioTemp = filasTemp.data.front;
                while (usuarioTemp != null) {
                    if (usuarioTemp.data == null) {
                        return null;
                    }
                    if (usuarioTemp.data.nombre.equals(usuarioRevisar.data.nombre)) {
                        casillasTemp += 1;
                    }

                    usuarioTemp = usuarioTemp.next;
                }
                filasTemp = filasTemp.next;
            }
            if (casillasTemp > casillas) {
                res = usuarioRevisar.data;
                casillas = casillasTemp;
            }
            usuarioRevisar = usuarioRevisar.next;
        }
        return res;
    }

    public Filas getFront() {
        return front;
    }

    public void setFront(Filas front) {
        this.front = front;
    }

    public Filas getRear() {
        return rear;
    }

    public void setRear(Filas rear) {
        this.rear = rear;
    }
}
