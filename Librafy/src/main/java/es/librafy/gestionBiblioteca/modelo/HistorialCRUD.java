package es.librafy.gestionBiblioteca.modelo;

import java.sql.*;
import es.librafy.gestionBiblioteca.util.*;

public class HistorialCRUD {

    // Crear un nuevo historial
    public void createHistorial(Historial historial) throws SQLException {
        String query = "INSERT INTO historial (usuario_id, titulo, fecha_prestamo, fecha_devolucion, estado) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, historial.getUsuario_id());
            stmt.setString(2, historial.getTitulo());
            stmt.setString(3, historial.getFecha_prestamo());
            stmt.setString(4, historial.getFecha_devolucion());
            stmt.setString(5, historial.getEstado());
            stmt.executeUpdate();
        }
    }

    // Obtener un historial por su ID
    public Historial getHistorial(int id_historial) throws SQLException {
        String query = "SELECT * FROM historial WHERE id_historial = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id_historial);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Historial(
                    rs.getInt("id_historial"),
                    rs.getInt("usuario_id"),
                    rs.getString("titulo"),
                    rs.getString("fecha_prestamo"),
                    rs.getString("fecha_devolucion"),
                    rs.getString("estado")
                );
            }
        }
        return null;
    }

    // Actualizar un historial existente
    public void updateHistorial(Historial historial) throws SQLException {
        String query = "UPDATE historial SET usuario_id = ?, titulo = ?, fecha_prestamo = ?, fecha_devolucion = ?, estado = ? WHERE id_historial = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, historial.getUsuario_id());
            stmt.setString(2, historial.getTitulo());
            stmt.setString(3, historial.getFecha_prestamo());
            stmt.setString(4, historial.getFecha_devolucion());
            stmt.setString(5, historial.getEstado());
            stmt.setInt(6, historial.getId_historial());
            stmt.executeUpdate();
        }
    }

    // Eliminar un historial por su ID
    public void deleteHistorial(int id_historial) throws SQLException {
        String query = "DELETE FROM historial WHERE id_historial = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id_historial);
            stmt.executeUpdate();
        }
    }
}
