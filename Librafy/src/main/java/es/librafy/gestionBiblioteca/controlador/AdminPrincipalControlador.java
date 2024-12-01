package es.librafy.gestionBiblioteca.controlador;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminPrincipalControlador {

    private Stage stage;
    private Scene scene;
    private Parent root;
    
    @FXML
    private Label lblNombre;
    
    @FXML
    private Button btn_volver;

    // Método para configurar el nombre del admin
    public void setNombreAdmin(String nombreAdmin) {
        lblNombre.setText(nombreAdmin); // Muestra el nombre del admin
    }
    

    @FXML
    private void volver() {
        try {
            // Cargar el archivo FXML de la ventana de inicio
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/es/librafy/gestionBiblioteca/vista/inicio.fxml"));
            Parent root = loader.load();

            // Crear una nueva escena y una nueva ventana para Inicio
            Stage inicioStage = new Stage();
            inicioStage.setTitle("Inicio");
            inicioStage.setScene(new Scene(root));
            inicioStage.show();

            // Cerrar la ventana actual
            Stage currentStage = (Stage) btn_volver.getScene().getWindow();
            currentStage.close();

            System.out.println("Volviendo a la ventana de inicio...");

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error al abrir la ventana de inicio.");
        }
    }

    
    // Método para cargar la interfaz de gestión de libros
    @FXML
    public void irGestionLibros(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/es/librafy/gestionBiblioteca/vista/GestionLibros.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    // Método para cargar la interfaz de gestión de solicitudes
    @FXML
    public void irGestionSolicitudes(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/es/librafy/gestionBiblioteca/vista/GestionSolicitudes.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    // Método para cargar la interfaz de gestión de usuarios
    @FXML
    public void irGestionUsuarios(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/es/librafy/gestionBiblioteca/vista/GestionUsuarios.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}

