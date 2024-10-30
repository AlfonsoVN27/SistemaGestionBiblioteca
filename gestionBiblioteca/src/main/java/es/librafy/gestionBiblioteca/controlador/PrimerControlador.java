package es.librafy.gestionBiblioteca.controlador;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import es.librafy.gestionBiblioteca.util.DatabaseConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class PrimerControlador {
	
	@FXML
    public void mostrarMensaje(ActionEvent event) {
        System.out.println("Botón presionado");
    }

	public void mostrarDatos() {
        Connection connection = DatabaseConnection.getConnection();
        
        if (connection != null) {
            try {
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM usuarios");
                
                while (resultSet.next()) {
                    // Reemplaza "columna" con los nombres reales de las columnas de tu tabla
                    System.out.println("Columna: " + resultSet.getString("nombre"));
                }
                
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
