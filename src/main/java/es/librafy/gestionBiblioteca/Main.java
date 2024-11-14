package es.librafy.gestionBiblioteca;

import es.librafy.gestionBiblioteca.controlador.InicioControlador;

public class Main {
    public static void main(String[] args) {
        InicioControlador controlador = new InicioControlador();
        controlador.mostrarDatos();
    }
}
