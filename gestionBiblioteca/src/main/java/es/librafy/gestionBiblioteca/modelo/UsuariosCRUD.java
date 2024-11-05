package es.librafy.gestionBiblioteca.modelo;

import java.sql.*;
import es.librafy.gestionBiblioteca.util.*;

public class UsuariosCRUD {
    public void createUsuario(Usuarios usuario) throws SQLException {
        String query = "INSERT INTO usuarios (nombre, fecha_nacimiento, email, num_telefono, dni) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getFecha_nacimiento());
            stmt.setString(3, usuario.getEmail());
            stmt.setString(4, usuario.getNum_telefono());
            stmt.setString(5, usuario.getDni());
            stmt.setString(6, usuario.getContrasena());
            stmt.executeUpdate();
        }
    }

    public Usuarios getUsuario(int id_usuario) throws SQLException {
        String query = "SELECT * FROM usuarios WHERE id_usuario = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id_usuario);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Usuarios(
                    rs.getInt("id_usuario"),
                    rs.getString("nombre"),
                    rs.getString("fecha_nacimiento"),
                    rs.getString("email"),
                    rs.getString("num_telefono"),
                    rs.getString("dni"),
                    rs.getString("contrasena")
                );
            }
        }
        return null;
    }

    public void updateUsuario(Usuarios usuario) throws SQLException {
        String query = "UPDATE usuarios SET nombre = ?, fecha_nacimiento = ?, email = ?, num_telefono = ?, dni = ? WHERE id_usuario = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getFecha_nacimiento());
            stmt.setString(3, usuario.getEmail());
            stmt.setString(4, usuario.getNum_telefono());
            stmt.setString(5, usuario.getDni());
            stmt.setInt(6, usuario.getId_usuario());
            stmt.setString(7, usuario.getContrasena());
            stmt.executeUpdate();
        }
    }

    public void deleteUsuario(int id_usuario) throws SQLException {
        String query = "DELETE FROM usuarios WHERE id_usuario = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id_usuario);
            stmt.executeUpdate();
        }
    }
}
