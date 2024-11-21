package es.librafy.gestionBiblioteca.modelo;

import java.sql.*;
import es.librafy.gestionBiblioteca.util.*;

public class HistorialCRUD {

    public void createHistorial(Historial historial) throws SQLException {
        String query = "INSERT INTO historial (usuario_id, libro_id, fecha_prestamo, fecha_devolucion) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, historial.getUsuario_id());
            stmt.setInt(2, historial.getLibro_id());
            stmt.setString(3, historial.getFecha_prestamo());
            stmt.setString(4, historial.getFecha_devolucion());
            stmt.executeUpdate();
        }
    }

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
                    rs.getInt("libro_id"),
                    rs.getString("fecha_prestamo"),
                    rs.getString("fecha_devolucion")
                );
            }
        }
        return null;
    }

    public void updateHistorial(Historial historial) throws SQLException {
        String query = "UPDATE historial SET usuario_id = ?, libro_id = ?, fecha_prestamo = ?, fecha_devolucion = ? WHERE id_historial = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, historial.getUsuario_id());
            stmt.setInt(2, historial.getLibro_id());
            stmt.setString(3, historial.getFecha_prestamo());
            stmt.setString(4, historial.getFecha_devolucion());
            stmt.setInt(5, historial.getId_historial());
            stmt.executeUpdate();
        }
    }

    public void deleteHistorial(int id_historial) throws SQLException {
        String query = "DELETE FROM historial WHERE id_historial = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id_historial);
            stmt.executeUpdate();
        }
    }
}
