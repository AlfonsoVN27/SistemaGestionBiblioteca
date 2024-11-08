package es.librafy.gestionBiblioteca.controlador;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.regex.Pattern;

import es.librafy.gestionBiblioteca.util.DatabaseConnection;

public class CrearCuentaControlador {
    
    @FXML
    private Button btn_volver;
    @FXML
    private Button btn_crearCuenta; 
    @FXML
    private TextField txt_nombre;
    @FXML
    private TextField txt_apellidos;
    @FXML
    private TextField txt_nombreUsuario;
    @FXML
    private TextField txt_direccion;
    @FXML
    private TextField txt_dni;
    @FXML
    private TextField txt_fnac;
    @FXML
    private TextField txt_email;
    @FXML
    private TextField txt_contraseña;
    @FXML
    private TextField txt_confirm;
    @FXML
    private TextField txt_ntelef;
    @FXML
    private Label lblMensaje;

    private Stage previousStage;

    // Establece la ventana anterior
    public void setPreviousStage(Stage stage) {
        this.previousStage = stage;
    }

    @FXML
    public void initialize() {
        btn_volver.setOnAction(event -> volver());
        // Configura el botón "Crear Cuenta" para llamar al método guardarUsuario
        btn_crearCuenta.setOnAction(this::guardarUsuario);
    }

    // Volver a la ventana anterior
    public void volver() {
        if (previousStage != null) {
            previousStage.show();
        }
        Stage currentStage = (Stage) btn_volver.getScene().getWindow();
        currentStage.close(); 
    }

    // Método que guarda el nuevo usuario en la base de datos
    @FXML
    public void guardarUsuario(ActionEvent event) {
        String nombreCompleto = txt_nombre.getText() + " " + txt_apellidos.getText();
        String nombreUsuario = txt_nombreUsuario.getText();
        String direccion = txt_direccion.getText();
        String dni = txt_dni.getText();
        String fechaNacimiento = txt_fnac.getText();
        String email = txt_email.getText();
        String contraseña = txt_contraseña.getText();
        String confirmContraseña = txt_confirm.getText();
        String numeroTelefono = txt_ntelef.getText();

        // Validación de campos
        if (!validarContraseña(contraseña, confirmContraseña)) {
            lblMensaje.setWrapText(true); // Asegura que el texto se ajuste
            lblMensaje.setPrefWidth(300); // Asegura un ancho adecuado
            lblMensaje.setText("La contraseña debe tener al menos 8 caracteres,\n" +
                               "una letra y un número, y coincidir."); // Texto dividido en líneas
            lblMensaje.setTextFill(Color.RED);
            return;
        }

        // Inserta los datos en la base de datos, asignando es_admin = 0
        try (Connection conn = DatabaseConnection.getConnection()) { // Asegúrate de adaptar esta línea
            String sql = "INSERT INTO usuarios (nombre, nomUsuario, direccion, dni, fecha_nacimiento, email, contrasena, num_telefono, es_admin) " +
                         "VALUES (?, ?, ?, ?, ?, ?, ?, ?, 0)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, nombreCompleto);
            stmt.setString(2, nombreUsuario);
            stmt.setString(3, direccion);
            stmt.setString(4, dni);
            stmt.setString(5, fechaNacimiento);
            stmt.setString(6, email);
            stmt.setString(7, contraseña);
            stmt.setString(8, numeroTelefono);

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                lblMensaje.setText("Usuario creado con éxito.\n" +
                        "Vuelva al incio para iniciar sesión.");
                lblMensaje.setTextFill(Color.GREEN);
            }
        } catch (SQLException e) {
            lblMensaje.setText("Error al guardar usuario: " + e.getMessage());
            lblMensaje.setTextFill(Color.RED);
            e.printStackTrace();
        }
    }

    // Valida que la contraseña cumpla los requisitos de seguridad
    private boolean validarContraseña(String contraseña, String confirmContraseña) {
        if (!contraseña.equals(confirmContraseña)) {
            return false;
        }
        
        // Verifica que tenga al menos 8 caracteres, una letra y un número
        return contraseña.length() >= 8 &&
               Pattern.compile("[A-Za-z]").matcher(contraseña).find() && // Contiene letras
               Pattern.compile("[0-9]").matcher(contraseña).find();      // Contiene números
    }
}
