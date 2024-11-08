package es.librafy.gestionBiblioteca.controlador;

import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import es.librafy.gestionBiblioteca.modelo.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class GestionSolicitudesControlador {

	/*@FXML
	private button modificarSol;*/
	
	@FXML
    private TableColumn<TablaVista, String> columnaTipo;
    @FXML
    private ComboBox<String> disponibilidad;
    @FXML
    private ComboBox<String> estado;
    @FXML
    private TableView<TablaVista> tablaSolicitudes;
    @FXML
    private TableColumn<TablaVista, String> columnaId;
    @FXML
    private TableColumn<TablaVista, String> columnaNombre;
    @FXML
    private TableColumn<TablaVista, String> columnaLibros;
    @FXML
    private TableColumn<TablaVista, String> columnaEntrega;
    @FXML
    private TableColumn<TablaVista, String> columnaDisponibilidad;
    @FXML
    private TableColumn<TablaVista, String> columnaEstado;
    
    private ObservableList<TablaVista> listaSolicitudes = FXCollections.observableArrayList();
	
	 @FXML
	 public void initialize() {
	     // Configuración de columnas
	     columnaId.setCellValueFactory(new PropertyValueFactory<>("id"));
	     columnaNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
	     columnaLibros.setCellValueFactory(new PropertyValueFactory<>("Libro"));
	     columnaEntrega.setCellValueFactory(new PropertyValueFactory<>("Entrega"));
	     columnaDisponibilidad.setCellValueFactory(new PropertyValueFactory<>("Disponibilidad"));
	     columnaEstado.setCellValueFactory(new PropertyValueFactory<>("Estado"));
	     
	     disponibilidad.setItems(FXCollections.observableArrayList("Disponible", "En Préstamo"));
	     estado.setItems(FXCollections.observableArrayList("Pendiente", "Aprobado", "Rechazado"));
	     
	     tablaSolicitudes.setItems(listaSolicitudes);
	 }
	 
	 @FXML
	 public void mostrarTablaEditable() {
	     // Configuración de columnas no editables
	     columnaId.setCellValueFactory(new PropertyValueFactory<>("id"));
	     columnaNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
	     columnaLibros.setCellValueFactory(new PropertyValueFactory<>("Libro"));
	     columnaEntrega.setCellValueFactory(new PropertyValueFactory<>("Entrega"));
	     
	     // Configurar la columna de Disponibilidad como ComboBoxTableCell para que sea editable
	     columnaDisponibilidad.setCellValueFactory(new PropertyValueFactory<>("disponibilidad"));
	     columnaDisponibilidad.setCellFactory(ComboBoxTableCell.forTableColumn(
	             FXCollections.observableArrayList("Disponible", "En Préstamo")));
	     columnaDisponibilidad.setOnEditCommit(event -> {
	         TablaVista solicitud = event.getRowValue();
	         solicitud.setDisponibilidad(event.getNewValue());
	     });

	     // Configurar la columna de Estado como ComboBoxTableCell para que sea editable
	     columnaEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
	     columnaEstado.setCellFactory(ComboBoxTableCell.forTableColumn(
	             FXCollections.observableArrayList("Pendiente", "Aprobado", "Rechazado")));
	     columnaEstado.setOnEditCommit(event -> {
	         TablaVista solicitud = event.getRowValue();
	         solicitud.setEstado(event.getNewValue());
	     });
	     
	     // Hacer la tabla editable y asignar la lista de elementos
	     tablaSolicitudes.setEditable(true);
	     tablaSolicitudes.setItems(listaSolicitudes);
	 }


	 private void cargarDatosDesdeBD() {
	        // Implementación de la carga de datos desde la base de datos
	    }
	public void modificarSol() {
		
	}
}