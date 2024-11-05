package es.librafy.gestionBiblioteca.modelo;

import java.sql.*;
import es.librafy.gestionBiblioteca.util.*;

public class LibrosCRUD {
    public void createLibro(Libros libro) throws SQLException {
        String query = "INSERT INTO libros (titulo, autor, fecha_prestamo, fecha_devolucion, categoria, estado) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, libro.getTitulo());
            stmt.setString(2, libro.getAutor());
            stmt.setString(3, libro.getFecha_prestamo());
            stmt.setString(4, libro.getFecha_devolucion());
            stmt.setString(5, libro.getCategoria());
            stmt.setString(6, libro.getEstado());
            stmt.executeUpdate();
        }
    }

    public Libros getLibro(int id_libro) throws SQLException {
        String query = "SELECT * FROM libros WHERE id_libro = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id_libro);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Libros(
                    rs.getInt("id_libro"),
                    rs.getString("titulo"),
                    rs.getString("autor"),
                    rs.getString("fecha_prestamo"),
                    rs.getString("fecha_devolucion"),
                    rs.getString("categoria"),
                    rs.getString("estado")
                );
            }
        }
        return null;
    }

    public void updateLibro(Libros libro) throws SQLException {
        String query = "UPDATE libros SET titulo = ?, autor = ?, fecha_prestamo = ?, fecha_devolucion = ?, categoria = ?, estado = ? WHERE id_libro = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, libro.getTitulo());
            stmt.setString(2, libro.getAutor());
            stmt.setString(3, libro.getFecha_prestamo());
            stmt.setString(4, libro.getFecha_devolucion());
            stmt.setString(5, libro.getCategoria());
            stmt.setString(6, libro.getEstado());
            stmt.setInt(7, libro.getId_libro());
            stmt.executeUpdate();
        }
    }

    public void deleteLibro(int id_libro) throws SQLException {
        String query = "DELETE FROM libros WHERE id_libro = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id_libro);
            stmt.executeUpdate();
        }
    }
}
