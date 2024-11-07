package es.librafy.gestionBiblioteca.modelo;

import java.sql.*;
import es.librafy.gestionBiblioteca.util.*;

public class SolicitudesCRUD {
    public void createSolicitud(Solicitudes solicitud) throws SQLException {
        String query = "INSERT INTO solicitudes (usuario_id, libro_id, modo_entrega, disponibilidad) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, solicitud.getUsuario_id());
            stmt.setInt(2, solicitud.getLibro_id());
            stmt.setString(3, solicitud.getModo_entrega());
            stmt.setString(4, solicitud.getDisponibilidad());
            stmt.setString(5, solicitud.getEstado());
            stmt.executeUpdate();
        }
    }

    public Solicitudes getSolicitud(int id_solicitud) throws SQLException {
        String query = "SELECT * FROM solicitudes WHERE id_solicitud = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id_solicitud);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Solicitudes(
                    rs.getInt("id_solicitud"),
                    rs.getInt("usuario_id"),
                    rs.getInt("libro_id"),
                    rs.getString("modo_entrega"),
                    rs.getString("disponibilidad"),
                    rs.getString("estado")
                );
            }
        }
        return null;
    }

    public void updateSolicitud(Solicitudes solicitud) throws SQLException {
        String query = "UPDATE solicitudes SET usuario_id = ?, libro_id = ?, modo_entrega = ?, disponibilidad = ? WHERE id_solicitud = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, solicitud.getUsuario_id());
            stmt.setInt(2, solicitud.getLibro_id());
            stmt.setString(3, solicitud.getModo_entrega());
            stmt.setString(4, solicitud.getDisponibilidad());
            stmt.setInt(5, solicitud.getId_solicitud());
            stmt.setString(6, solicitud.getEstado());
            stmt.executeUpdate();
        }
    }

    public void deleteSolicitud(int id_solicitud) throws SQLException {
        String query = "DELETE FROM solicitudes WHERE id_solicitud = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id_solicitud);
            stmt.executeUpdate();
        }
    }
}
