package com.example.prueba;

public class NodoUsuario {
    Usuario data;
    NodoUsuario next;

    public NodoUsuario(Usuario data) {
        this.data = data;
        this.next = null;
    }
}

