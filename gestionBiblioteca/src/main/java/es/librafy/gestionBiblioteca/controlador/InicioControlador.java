package es.librafy.gestionBiblioteca.controlador;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import es.librafy.gestionBiblioteca.util.DatabaseConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class InicioControlador {
		
	@FXML
    private Button btn_nuevaCuenta;
	
	@FXML
    private Button btn_usuario;
	
	@FXML
    private Button btn_admin;
	
	@FXML	
    public void mostrarMensaje(ActionEvent event) {
        System.out.println("Botón presionado");
    }
	
	public void abrirISAdmin() {
		try {
	        FXMLLoader loader = new FXMLLoader(getClass().getResource("/es/librafy/gestionBiblioteca/vista/inicio_sesion_admin.fxml"));
	        Parent root = loader.load();

	        // Obtener el controlador de la interfaz de inicio de sesión del admin
	        ISAdminControlador controller = loader.getController();

	        // Pasar la referencia de la ventana anterior
	        Stage currentStage = (Stage) btn_admin.getScene().getWindow();
	        controller.setPreviousStage(currentStage);

	        Stage stage = new Stage();
	        stage.setTitle("Inicio de Sesión Admin");
	        stage.setScene(new Scene(root));
	        stage.show();
	        
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	public void abrirISUsuario() {
		try {
	        FXMLLoader loader = new FXMLLoader(getClass().getResource("/es/librafy/gestionBiblioteca/vista/inicio_sesion_usuario.fxml"));
	        Parent root = loader.load();

	        // Obtener el controlador de la interfaz de inicio de sesión del admin
	        ISUsuarioControlador controller = loader.getController();

	        // Pasar la referencia de la ventana anterior
	        Stage currentStage = (Stage) btn_usuario.getScene().getWindow();
	        controller.setPreviousStage(currentStage);

	        Stage stage = new Stage();
	        stage.setTitle("Inicio de Sesión Usuario");
	        stage.setScene(new Scene(root));
	        stage.show();
	        
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
    }
	
	public void abrirCrearCuenta() {
		try {
	        FXMLLoader loader = new FXMLLoader(getClass().getResource("/es/librafy/gestionBiblioteca/vista/crear_cuenta.fxml"));
	        Parent root = loader.load();

	        // Obtener el controlador de la interfaz de inicio de sesión del admin
	        CrearCuentaControlador controller = loader.getController();

	        // Pasar la referencia de la ventana anterior
	        Stage currentStage = (Stage) btn_nuevaCuenta.getScene().getWindow();
	        controller.setPreviousStage(currentStage);

	        Stage stage = new Stage();
	        stage.setTitle("Crear nueva cuenta de usuario");
	        stage.setScene(new Scene(root));
	        stage.show();
	        
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
    }

	public void mostrarDatos() {
        Connection connection = DatabaseConnection.getConnection();
        
        if (connection != null) {
            try {
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM usuarios");
                
                while (resultSet.next()) {
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
