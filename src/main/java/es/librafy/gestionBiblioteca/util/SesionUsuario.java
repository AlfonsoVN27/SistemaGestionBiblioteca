package es.librafy.gestionBiblioteca.util;

import java.util.List;

import es.librafy.gestionBiblioteca.modelo.Libros;

public class SesionUsuario {
    private static String usuarioActual;
    private static List<Libros> resultadosBusqueda;

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

