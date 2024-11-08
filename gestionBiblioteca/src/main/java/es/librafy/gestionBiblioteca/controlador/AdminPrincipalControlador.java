package es.librafy.gestionBiblioteca.controlador;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminPrincipalControlador {

    private Stage stage;
    private Scene scene;
    private Parent root;
    
    @FXML
    private Label lblNombre; // Label para mostrar el nombre del admin

    // Método para configurar el nombre del admin
    public void setNombreAdmin(String nombreAdmin) {
        lblNombre.setText(nombreAdmin); // Muestra el nombre del admin
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

