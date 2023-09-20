package com.example.prueba;

public class Cola {
    private NodoUsuario front;
    private NodoUsuario rear;

    public Cola() {
        this.front = null;
        this.rear = null;
    }

    public void enqueue(Usuario data) {
        NodoUsuario newNode = new NodoUsuario(data);

        if (isEmpty()) {
            front = newNode;
            rear = newNode;
        } else {
            rear.next = newNode;
            rear = newNode;
        }
    }

    public Usuario dequeue() {
        if (isEmpty()) {
            throw new IllegalStateException("Queue is empty");
        }

        Usuario data = front.data;
        front = front.next;

        if (front == null) {
            rear = null;
        }

        return data;
    }

    public boolean isEmpty() {
        return front == null;
    }

    public void display() {
        NodoUsuario current = front;
        while (current != null) {
            System.out.print(current.data.nombre + " -> ");
            current = current.next;
        }
        System.out.println("null");
    }
}
