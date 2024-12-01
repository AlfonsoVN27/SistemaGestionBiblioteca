package es.librafy.gestionBiblioteca.controlador;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import es.librafy.gestionBiblioteca.util.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ModificarCuentaControlador {
    
    @FXML
    private TextField txtNomUsuario, txtEmail, txtNumTelf, txtDireccion, txtContrasena;
    
    @FXML
    private Button btn_modificarDatos;

    private String usuarioActual;

    // Método para cargar los datos del usuario en los TextFields
    public void cargarDatosParaModificar(String usuario, String email, String numTelefono, String direccion, String contrasena) {
        this.usuarioActual = usuario;

        txtNomUsuario.setText(usuario);
        txtEmail.setText(email);
        txtNumTelf.setText(numTelefono);
        txtDireccion.setText(direccion);
        txtContrasena.setText(contrasena);
    }

    // Método para actualizar los datos en la base de datos
    @FXML
    private void modificarCuenta() {
        // Obtener los valores de los TextFields
        String nuevoNombreUsuario = txtNomUsuario.getText();
        String nuevoEmail = txtEmail.getText();
        String nuevoTelefono = txtNumTelf.getText();
        String nuevaDireccion = txtDireccion.getText();
        String nuevaContrasena = txtContrasena.getText();

        String query = "UPDATE usuarios SET nombre = ?, email = ?, num_telefono = ?, direccion = ?, contrasena = ? WHERE nomUsuario = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            // Asignar los parámetros
            statement.setString(1, nuevoNombreUsuario);
            statement.setString(2, nuevoEmail);
            statement.setString(3, nuevoTelefono);
            statement.setString(4, nuevaDireccion);
            statement.setString(5, nuevaContrasena);
            statement.setString(6, usuarioActual); // `usuarioActual` es el nombre de usuario actual del usuario

            // Ejecutar la actualización
            int rowsUpdated = statement.executeUpdate();

            if (rowsUpdated > 0) {
                // Mostrar un mensaje de éxito
                mostrarMensajeExito();

                // Cerrar la ventana de modificación
                Stage stage = (Stage) btn_modificarDatos.getScene().getWindow();
                stage.close();
            } else {
                // Si no se actualizan los datos, puedes mostrar un mensaje de error (opcional)
                mostrarMensajeError();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            // Manejo de error si no se pudo actualizar
            mostrarMensajeError();
        }
    }

    private void mostrarMensajeExito() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Éxito");
        alert.setHeaderText(null); // No necesitamos un encabezado
        alert.setContentText("Los datos se han actualizado correctamente.");
        alert.showAndWait(); // Mostrar el mensaje y esperar a que el usuario lo cierre
    }

    private void mostrarMensajeError() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null); // No necesitamos un encabezado
        alert.setContentText("Hubo un problema al actualizar los datos. Inténtalo de nuevo.");
        alert.showAndWait(); // Mostrar el mensaje y esperar a que el usuario lo cierre
    }


}
