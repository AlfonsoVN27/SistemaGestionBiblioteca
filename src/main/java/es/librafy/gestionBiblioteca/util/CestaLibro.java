package es.librafy.gestionBiblioteca.util;

import es.librafy.gestionBiblioteca.modelo.Libros;

public class CestaLibro {
    private Libros libro;
    private int cantidad;

    public CestaLibro(Libros libro) {
        this.libro = libro;
        this.cantidad = 1;  // Empezamos con una cantidad de 1
    }

    public Libros getLibro() {
        return libro;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void incrementarCantidad() {
        cantidad++;
    }

    public void decrementarCantidad() {
        if (cantidad > 1) {
            cantidad--;
        }
    }
}
