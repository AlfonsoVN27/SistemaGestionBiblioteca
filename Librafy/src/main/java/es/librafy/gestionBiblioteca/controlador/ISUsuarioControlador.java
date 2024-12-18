package es.librafy.gestionBiblioteca.controlador;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

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
            abrirInterfazPrincipal(usuario);
        } else {
            mostrarMensajeError("Usuario o contraseña incorrectos");
        }
    }

    private boolean verificarCredenciales(String usuario, String contrasena) {
        // Consulta SQL actualizada para aceptar tanto nomUsuario como email
        String query = "SELECT COUNT(*) FROM usuarios WHERE (nomUsuario = ? OR email = ?) AND contrasena = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, usuario);  // El valor ingresado, puede ser nomUsuario o email
            statement.setString(2, usuario);  // Lo mismo para email (aceptando ambos)
            statement.setString(3, contrasena);  // Contraseña

            ResultSet resultSet = statement.executeQuery();

            // Verificamos si la consulta devuelve un resultado que indique que las credenciales son correctas
            return resultSet.next() && resultSet.getInt(1) > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    private void abrirInterfazPrincipal(String usuario) {
        try {
            // Cargar la ventana de la interfaz principal
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/es/librafy/gestionBiblioteca/vista/InterfazPrincipal.fxml"));
            Scene scenePrincipal = new Scene(loader.load());

            // Obtener el controlador de la interfaz principal
            InterfazPrincipalControlador interfazControlador = loader.getController();
            interfazControlador.setUsuarioActual(usuario);

            // Crear y mostrar la ventana de la interfaz principal
            Stage stagePrincipal = new Stage();
            stagePrincipal.setScene(scenePrincipal);
            stagePrincipal.setTitle("Interfaz Principal");
            stagePrincipal.show();
            
         // Cerrar la ventana de inicio de sesión actual
            Stage currentStage = (Stage) btn_inicioSesion.getScene().getWindow();
            currentStage.close(); // Cierra la ventana de inicio de sesión

            // Aplicar el efecto de oscurecimiento a la interfaz principal
            ColorAdjust grayEffect = new ColorAdjust();
            grayEffect.setBrightness(-0.5);  // Ajuste para oscurecer la interfaz principal
            stagePrincipal.getScene().getRoot().setEffect(grayEffect);

         // Cargar y abrir la ventana de Novedades sin barra de título
            FXMLLoader loaderNovedades = new FXMLLoader(getClass().getResource("/es/librafy/gestionBiblioteca/vista/Novedades.fxml"));
            Scene sceneNovedades = new Scene(loaderNovedades.load());
            Stage stageNovedades = new Stage();
            stageNovedades.setScene(sceneNovedades);
            stageNovedades.initStyle(StageStyle.UNDECORATED); // Configuración sin barra de título
            stageNovedades.initModality(Modality.WINDOW_MODAL);
            stageNovedades.initOwner(stagePrincipal); // Hace que esté vinculada a la interfaz principal

            // Obtener el controlador de la ventana de Novedades y pasarle el stage principal
            NovedadesControlador novedadesControlador = loaderNovedades.getController();
            novedadesControlador.setStagePrincipal(stagePrincipal);

            // Definir la posición personalizada para la ventana de novedades
            double x = 510;  // Desplazamiento horizontal (ajústalo a tu gusto)
            double y = 100;  // Desplazamiento vertical (ajústalo a tu gusto)

            // Establecer la nueva posición de la ventana de novedades
            stageNovedades.setX(x);  // Establece la posición X
            stageNovedades.setY(y);  // Establece la posición Y

            // Mostrar la ventana de novedades de forma modal (espera a que se cierre)
            stageNovedades.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
            mostrarMensajeError("Error al abrir la interfaz principal o novedades");
        }
    }




    private void mostrarMensajeExito(String mensaje) {
        lblMensaje.setText(mensaje);
        lblMensaje.setTextFill(Color.GREEN);
        lblMensaje.setVisible(true);
    }

    private void mostrarMensajeError(String mensaje) {
        lblMensaje.setText(mensaje);
        lblMensaje.setTextFill(Color.RED);
        lblMensaje.setVisible(true);
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
