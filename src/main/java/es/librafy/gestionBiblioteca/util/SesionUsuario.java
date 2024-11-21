package es.librafy.gestionBiblioteca.util;

import java.util.ArrayList;
import java.util.List;

import es.librafy.gestionBiblioteca.modelo.Libros;

public class SesionUsuario {
    private static String usuarioActual;
    private static List<Libros> resultadosBusqueda;
    private static List<Libros> cestaLibros = new ArrayList<>();
    
    // Método para obtener la cesta del usuario
    public static List<Libros> getLibrosEnCesta() {
        return cestaLibros;
    }

    // Método para añadir un libro a la cesta
    public static void añadirALaCesta(Libros libro) {
        cestaLibros.add(libro);
    }

    // Método para eliminar un libro de la cesta
    public static void eliminarDeLaCesta(Libros libro) {
        cestaLibros.remove(libro);
    }

    // Método para obtener el usuario actual
    public static String getUsuarioActual() {
        return usuarioActual;
    }

    // Método para establecer el usuario actual
    public static void setUsuarioActual(String usuario) {
        usuarioActual = usuario;
    }
    
    public static void setResultadosBusqueda(List<Libros> resultados) {
        resultadosBusqueda = resultados;
    }

    public static List<Libros> getResultadosBusqueda() {
        return resultadosBusqueda;
    }
}

