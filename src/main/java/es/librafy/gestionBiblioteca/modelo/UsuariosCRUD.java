package es.librafy.gestionBiblioteca.modelo;

import java.sql.*;
import es.librafy.gestionBiblioteca.util.*;

public class UsuariosCRUD {

    // Método para crear un usuario en la base de datos
    public void createUsuario(Usuarios usuario) throws SQLException {
        String query = "INSERT INTO usuarios (nombre, fecha_nacimiento, email, num_telefono, dni, contrasena, direccion, nomUsuario) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getFecha_nacimiento());
            stmt.setString(3, usuario.getEmail());
            stmt.setString(4, usuario.getNum_telefono());
            stmt.setString(5, usuario.getDni());
            stmt.setString(6, usuario.getContrasena());
            stmt.setString(7, usuario.getDireccion());
            stmt.setString(8, usuario.getNomUsuario());
            stmt.executeUpdate();
        }
    }

    // Método para obtener un usuario por su ID
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
                    rs.getString("contrasena"),
                    rs.getString("direccion"),
                    rs.getString("nomUsuario")
                );
            }
        }
        return null;
    }

    // Método para actualizar un usuario en la base de datos
    public void updateUsuario(Usuarios usuario) throws SQLException {
        String query = "UPDATE usuarios SET nombre = ?, fecha_nacimiento = ?, email = ?, num_telefono = ?, dni = ?, contrasena = ?, direccion = ?, nomUsuario = ? WHERE id_usuario = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getFecha_nacimiento());
            stmt.setString(3, usuario.getEmail());
            stmt.setString(4, usuario.getNum_telefono());
            stmt.setString(5, usuario.getDni());
            stmt.setString(6, usuario.getContrasena());
            stmt.setString(7, usuario.getDireccion());
            stmt.setString(8, usuario.getNomUsuario());
            stmt.setInt(9, usuario.getId_usuario());
            stmt.executeUpdate();
        }
    }

    // Método para eliminar un usuario por su ID
    public void deleteUsuario(int id_usuario) throws SQLException {
        String query = "DELETE FROM usuarios WHERE id_usuario = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id_usuario);
            stmt.executeUpdate();
        }
    }
}
