package es.librafy.gestionBiblioteca.controlador;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import es.librafy.gestionBiblioteca.util.DatabaseConnection;

public class ISUsuarioControlador {

    @FXML
    private Button btn_volver;

    @FXML
    private Button btn_inicioSesion; // Botón de inicio de sesión

    @FXML
    private TextField txt_usuario; // Campo de usuario

    @FXML
    private TextField txt_contrasena; // Campo de contraseña

    //Referencia a la ventana anterior
    private Stage previousStage;

    //Método para establecer la ventana anterior
    public void setPreviousStage(Stage stage) {
        this.previousStage = stage;
    }

    @FXML
    public void mostrarMensaje(ActionEvent event) {
        System.out.println("Botón presionado");
    }

    @FXML
    public void initialize() {
        btn_volver.setOnAction(event -> volver()); //Configura la acción del botón volver
        btn_inicioSesion.setOnAction(this::handleLoginButton); // Configura la acción del botón de inicio de sesión
    }

    public void volver() {
        if (previousStage != null) {
            previousStage.show();
        }
        Stage currentStage = (Stage) btn_volver.getScene().getWindow();
        currentStage.close(); 
    }

    public void handleLoginButton(ActionEvent event) {
        String usuario = txt_usuario.getText();
        String contrasena = txt_contrasena.getText();

        // Verifica las credenciales
        if (verificarCredenciales(usuario, contrasena)) {
            abrirInterfazPrincipal();
        } else {
            mostrarAlertaError();
        }
    }

    private boolean verificarCredenciales(String usuario, String contrasena) {
        String query = "SELECT COUNT(*) FROM usuarios WHERE nombre = ? AND contrasena = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
             
            statement.setString(1, usuario);
            statement.setString(2, contrasena);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt(1) > 0; // Si cuenta > 0, usuario y contraseña coinciden
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private void abrirInterfazPrincipal() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/es/librafy/gestionBiblioteca/vista/InterfazPrincipal.fxml"));
            Scene scenePrincipal = new Scene(loader.load());

            Stage stagePrincipal = new Stage();
            stagePrincipal.setScene(scenePrincipal);
            stagePrincipal.show();

            // Cerrar la ventana de inicio de sesión
            Stage currentStage = (Stage) btn_inicioSesion.getScene().getWindow();
            currentStage.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void mostrarAlertaError() {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle("Error de inicio de sesión");
        alerta.setHeaderText("Usuario o contraseña incorrectos");
        alerta.setContentText("Por favor, verifique sus credenciales e intente nuevamente.");
        alerta.showAndWait();
    }
}
