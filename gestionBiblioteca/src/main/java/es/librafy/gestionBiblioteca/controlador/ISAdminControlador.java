package es.librafy.gestionBiblioteca.controlador;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import es.librafy.gestionBiblioteca.util.DatabaseConnection;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

public class ISAdminControlador {

    @FXML
    private TextField txt_admin;
    
    @FXML
    private TextField txt_contrasena;
    
    @FXML
    private Button btn_volver;
    
    @FXML
    private Button btn_inicioSesion;
    
    @FXML
    private Label lblMensaje;
    
    private String nombreAdmin;

    // Variable para almacenar la referencia de la ventana anterior
    private Stage previousStage;

    // Método para establecer la ventana anterior
    public void setPreviousStage(Stage stage) {
        this.previousStage = stage;
    }

    @FXML
    private void handleLoginButton() {
        String usuario = txt_admin.getText();
        String contrasena = txt_contrasena.getText();
        
        if (usuario.isEmpty() || contrasena.isEmpty()) {
            lblMensaje.setText("Por favor, complete todos los campos.");
            lblMensaje.setTextFill(Color.RED);
            return;
        }

        String query = "SELECT nombre FROM usuarios WHERE nomUsuario = ? AND contrasena = ? AND es_admin = 1";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, usuario);
            stmt.setString(2, contrasena);
            
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                nombreAdmin = rs.getString("nombre"); // Guarda el nombre del admin
                lblMensaje.setText("Inicio de sesión exitoso.");
                lblMensaje.setTextFill(Color.GREEN);
                abrirInterfazAdmin(); // Llama al método para abrir la interfaz de admin
            } else {
                lblMensaje.setText("Usuario o contraseña incorrectos o no tienes privilegios de administrador.");
                lblMensaje.setTextFill(Color.RED);
            }
            
        } catch (SQLException e) {
            lblMensaje.setText("Error de conexión con la base de datos.");
            lblMensaje.setTextFill(Color.RED);
            System.err.println("Error al verificar las credenciales: " + e.getMessage());
        }
    }

    private void abrirInterfazAdmin() {
        lblMensaje.setText("Inicio de sesión exitoso.");
        lblMensaje.setTextFill(Color.GREEN);

        PauseTransition delay = new PauseTransition(Duration.seconds(1.2));
        delay.setOnFinished(event -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/es/librafy/gestionBiblioteca/vista/AdminPrincipal.fxml"));
                Parent root = loader.load();

                // Obtener el controlador de la nueva interfaz y pasarle el nombre del admin
                AdminPrincipalControlador adminController = loader.getController();
                adminController.setNombreAdmin(nombreAdmin);

                // Crear y mostrar la nueva ventana de la interfaz principal de administración
                Stage adminStage = new Stage();
                adminStage.setTitle("Interfaz Principal del Admin");
                adminStage.setScene(new Scene(root));
                adminStage.show();

                // Cerrar la ventana de inicio de sesión actual
                Stage currentStage = (Stage) btn_inicioSesion.getScene().getWindow();
                currentStage.close();

                System.out.println("Accediendo a la interfaz principal del administrador...");
            } catch (IOException e) {
                e.printStackTrace();
                lblMensaje.setText("Error al abrir la interfaz principal.");
                lblMensaje.setTextFill(Color.RED);
            }
        });

        // Iniciar la pausa para dar tiempo al mensaje de éxito antes de cerrar
        delay.play();
    }

    @FXML
    private void volver() {
        if (previousStage != null) {
            previousStage.show(); 
        }
        Stage stage = (Stage) btn_volver.getScene().getWindow();
        stage.close();
        System.out.println("Volviendo a la pantalla anterior...");
    }
}
