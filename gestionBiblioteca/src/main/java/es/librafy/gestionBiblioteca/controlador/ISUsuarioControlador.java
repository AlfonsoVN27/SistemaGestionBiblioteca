package es.librafy.gestionBiblioteca.controlador;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
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
    private Button btn_inicioSesion;

    @FXML
    private Button btn_creaAhora;

    @FXML
    private TextField txt_usuario;

    @FXML
    private TextField txt_contrasena;

    @FXML
    private Label lblMensaje;

    private Stage previousStage;

    public void setPreviousStage(Stage stage) {
        this.previousStage = stage;
    }

    @FXML
    public void initialize() {
        btn_volver.setOnAction(event -> volver());
        btn_inicioSesion.setOnAction(this::handleLoginButton);
        btn_creaAhora.setOnAction(this::crearCuentaAhora);
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

        if (verificarCredenciales(usuario, contrasena)) {
            mostrarMensajeExito("Inicio de sesión exitoso");
            abrirInterfazPrincipal();
        } else {
            mostrarMensajeError("Usuario o contraseña incorrectos");
        }
    }

    private boolean verificarCredenciales(String usuario, String contrasena) {
        String query = "SELECT COUNT(*) FROM usuarios WHERE nomUsuario = ? AND contrasena = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, usuario);
            statement.setString(2, contrasena);
            ResultSet resultSet = statement.executeQuery();

            return resultSet.next() && resultSet.getInt(1) > 0;
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

            Stage currentStage = (Stage) btn_inicioSesion.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void mostrarMensajeExito(String mensaje) {
        lblMensaje.setText(mensaje);
        lblMensaje.setTextFill(Color.GREEN); // Cambia el color del texto a verde
        lblMensaje.setVisible(true); // Hace visible el Label
    }

    private void mostrarMensajeError(String mensaje) {
        lblMensaje.setText(mensaje);
        lblMensaje.setTextFill(Color.RED); // Cambia el color del texto a rojo
        lblMensaje.setVisible(true); // Hace visible el Label
    }

    private void crearCuentaAhora(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/es/librafy/gestionBiblioteca/vista/crear_cuenta.fxml"));
            Scene crearCuentaScene = new Scene(loader.load());

            Stage crearCuentaStage = new Stage();
            crearCuentaStage.setScene(crearCuentaScene);
            crearCuentaStage.setTitle("Crear Cuenta");

            crearCuentaStage.show();

            Stage currentStage = (Stage) btn_creaAhora.getScene().getWindow();
            currentStage.close();

        } catch (IOException e) {
            e.printStackTrace();
            mostrarMensajeError("Error al abrir la ventana de creación de cuenta");
        }
    }
}
