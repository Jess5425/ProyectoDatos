package com.example.prueba;

class ListaE {
    Nodo head;

    public ListaE() {
        this.head = null;
    }

    public void insert(int data) {
        Nodo newNode = new Nodo(data);

        if (head == null) {
            head = newNode;
        } else {
            Nodo current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
    }

    public void delete(int data) {
        if (head == null) {
            return;
        }

        if (head.data == data) {
            head = head.next;
            return;
        }

        Nodo current = head;
        while (current.next != null && current.next.data != data) {
            current = current.next;
        }

        if (current.next != null) {
            current.next = current.next.next;
        }
    }

    public void display() {
        Nodo current = head;
        while (current != null) {
            System.out.print(current.data + " -> ");
            current = current.next;
        }
        System.out.println("null");
    }
}