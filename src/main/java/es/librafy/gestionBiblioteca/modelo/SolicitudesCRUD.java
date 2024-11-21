package es.librafy.gestionBiblioteca.modelo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import es.librafy.gestionBiblioteca.util.*;

public class SolicitudesCRUD {
	
	 public List<Solicitudes> obtenerTodasLasSolicitudes() throws SQLException {
	        List<Solicitudes> listaSolicitudes = new ArrayList<>();

	        String query = "SELECT id_solicitud, usuario_id, libro_id, modo_entrega, disponibilidad, estado FROM solicitudes";

	        try (Connection conn = DatabaseConnection.getConnection();
	             Statement stmt = conn.createStatement();
	             ResultSet rs = stmt.executeQuery(query)) {

	            while (rs.next()) {
	                Solicitudes solicitud = new Solicitudes(
	                        rs.getInt("id_solicitud"),
	                        rs.getInt("usuario_id"),
	                        rs.getInt("libro_id"),
	                        rs.getString("modo_entrega"),
	                        rs.getString("disponibilidad"),
	                        rs.getString("estado")
	                );
	                listaSolicitudes.add(solicitud);
	            }
	        }
	        return listaSolicitudes;
	    }
	 
	 

	
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

    public void deleteSolicitud(int idSolicitud) throws SQLException {
	    String sql = "DELETE FROM solicitudes WHERE id_solicitud = ?";
	    try (Connection conn = DatabaseConnection.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {
	        
	        // Asignar el ID de la solicitud al parámetro de la consulta
	        stmt.setInt(1, idSolicitud);
	        
	        // Ejecutar la eliminación
	        stmt.executeUpdate();
	        
	        System.out.println("Solicitud con ID " + idSolicitud + " eliminada de la base de datos.");
	    }
	}
    
    public void updateSolicitud(Solicitudes solicitud) throws SQLException {
        // Modificada la consulta para que coincidan los parámetros
        String query = "UPDATE solicitudes SET usuario_id = ?, libro_id = ?, modo_entrega = ?, disponibilidad = ?, estado = ? WHERE id_solicitud = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            // Establecer los parámetros de la consulta
            stmt.setInt(1, solicitud.getUsuario_id());   // Establece el usuario_id
            stmt.setInt(2, solicitud.getLibro_id());     // Establece el libro_id
            stmt.setString(3, solicitud.getModo_entrega()); // Establece el modo_entrega
            stmt.setString(4, solicitud.getDisponibilidad()); // Establece la disponibilidad
            stmt.setString(5, solicitud.getEstado());    // Establece el estado
            stmt.setInt(6, solicitud.getId_solicitud()); // Establece el id_solicitud como condición para la actualización
            
            // Ejecutar la actualización
            stmt.executeUpdate();
        }
    }
}