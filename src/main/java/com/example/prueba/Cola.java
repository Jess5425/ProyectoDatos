package com.example.prueba;

class Cola {
    private Nodo front;
    private Nodo rear;

    public Cola() {
        this.front = null;
        this.rear = null;
    }

    public Cola(int[] elementos) {
        this();
        for (int elemento : elementos) {
            enqueue(elemento);
        }
    }

    public void enqueue(int data) {
        Nodo newNode = new Nodo(data);

        if (isEmpty()) {
            front = newNode;
            rear = newNode;
        } else {
            rear.next = newNode;
            rear = newNode;
        }
    }

    public int dequeue() {
        if (isEmpty()) {
            throw new IllegalStateException("Queue is empty");
        }

        int data = front.data;
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
        Nodo current = front;
        while (current != null) {
            System.out.print(current.data + " -> ");
            current = current.next;
        }
        System.out.println("null");
    }
}