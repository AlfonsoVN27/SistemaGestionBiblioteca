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
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.util.converter.IntegerStringConverter;




public class GestionLibrosControlador {
	@FXML
	private Button btnAñadir;
	@FXML
	private Button btnModificar;
	@FXML
	private Button btnEliminar;
    @FXML
    private TextField idLibro;
    @FXML
    private TextField titulo;
    @FXML
    private TextField autor;
    @FXML
    private TextField fechaPres;
    @FXML
    private TextField fechaDev;
    @FXML
    private ComboBox<String> categoria;
    @FXML
    private ComboBox<String> estado;
    @FXML
    private TableView<TablaVista> tablaLibros;
    @FXML
    private TableColumn<TablaVista, String> idColumn;
    @FXML
    private TableColumn<TablaVista, String> tituloColumn;
    @FXML
    private TableColumn<TablaVista, String> autorColumn;
    @FXML
    private TableColumn<TablaVista, String> fechapresColumn;
    @FXML
    private TableColumn<TablaVista, String> fechadelColumn;
    @FXML
    private TableColumn<TablaVista, String> categoriaColumn;
    @FXML
    private TableColumn<TablaVista, String> estadoColumn;
    
    private ObservableList<TablaVista> listaLibros = FXCollections.observableArrayList();

    
    @FXML
	 public void initialize() {
	     // Configuración de columnas
	     idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
	     tituloColumn.setCellValueFactory(new PropertyValueFactory<>("titulo"));
	     autorColumn.setCellValueFactory(new PropertyValueFactory<>("autor"));
	     fechapresColumn.setCellValueFactory(new PropertyValueFactory<>("fecha prestamo"));
	     fechadelColumn.setCellValueFactory(new PropertyValueFactory<>("fecha devolución"));
	     categoriaColumn.setCellValueFactory(new PropertyValueFactory<>("categoria"));
	     estadoColumn.setCellValueFactory(new PropertyValueFactory<>("estado"));

	     
	     categoria.setItems(FXCollections.observableArrayList("Ficción", "Clásico", "Distopía", "Infantil", "Misterio"));
	     estado.setItems(FXCollections.observableArrayList("Devuelto", "En prestamo", "Rechazado"));
	     
	     tablaLibros.setItems(listaLibros);
	 }
    @FXML
    public void mostrarTablaEditable() {
        // Permitir que la tabla sea editable
        tablaLibros.setEditable(true);

        // Configuración de columnas editables
        idColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        idColumn.setOnEditCommit(event -> {
            event.getRowValue().setId(event.getNewValue());
        });

        tituloColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        tituloColumn.setOnEditCommit(event -> {
            event.getRowValue().setTitulo(event.getNewValue());
        });


        autorColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        autorColumn.setOnEditCommit(event -> {
            event.getRowValue().setAutor(event.getNewValue());
        });


        fechapresColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        fechapresColumn.setOnEditCommit(event -> {
            event.getRowValue().setFechaPrestamo(event.getNewValue());
        });

        fechadelColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        fechadelColumn.setOnEditCommit(event -> {
            event.getRowValue().setFechaDevolucion(event.getNewValue());
        });

        
        categoriaColumn.setCellFactory(ComboBoxTableCell.forTableColumn(
        	    FXCollections.observableArrayList("Ficción", "Clásico", "Distopía", "Infantil", "Misterio")
        	));
        	categoriaColumn.setOnEditCommit(event -> {
        	    event.getRowValue().setCategoria(event.getNewValue());
        	});

        	// Configuración de la columna estadoColumn como ComboBox editable
        	estadoColumn.setCellFactory(ComboBoxTableCell.forTableColumn(
        	    FXCollections.observableArrayList("Devuelto", "En prestamo", "Rechazado")
        	));
        	estadoColumn.setOnEditCommit(event -> {
        	    event.getRowValue().setEstado(event.getNewValue());
        	});
    }
    
    @FXML
    public void añadirLibro() {
        // Crear un nuevo libro a partir de los campos de entrada
        String id = idLibro.getText();
        String tituloLibro = titulo.getText();
        Integer autorLibro = Integer.parseInt(autor.getText());
        String fechaPrestamo = fechaPres.getText();
        String fechaDevolucion = fechaDev.getText();
        String categoriaLibro = categoria.getValue();
        String estadoLibro = estado.getValue();

        // Crear y añadir un nuevo objeto TablaVista a la lista
        TablaVista nuevoLibro = new TablaVista(autorLibro, id, estadoLibro, estadoLibro, estadoLibro, estadoLibro, estadoLibro, autorLibro, fechaPrestamo, fechaDevolucion, estadoLibro, estadoLibro, estadoLibro, estadoLibro, autorLibro, autorLibro, autorLibro, estadoLibro, estadoLibro, autorLibro, estadoLibro, estadoLibro, estadoLibro);
        nuevoLibro.setTitulo(tituloLibro);
        nuevoLibro.setCategoria(categoriaLibro);
        nuevoLibro.setEstado(estadoLibro);

        listaLibros.add(nuevoLibro);

        // Limpiar campos de entrada después de añadir
        limpiarCampos();
    }

    @FXML
    public void modificarLibro() {
        // Obtener el libro seleccionado en la tabla
        TablaVista libroSeleccionado = tablaLibros.getSelectionModel().getSelectedItem();

        if (libroSeleccionado != null) {
            // Actualizar el objeto con los valores actuales de los campos de entrada
            libroSeleccionado.setId(idLibro.getText());
            libroSeleccionado.setTitulo(titulo.getText());
            libroSeleccionado.setAutor(autor.getText());
            libroSeleccionado.setFechaPrestamo(fechaPres.getText());
            libroSeleccionado.setFechaDevolucion(fechaDev.getText());
            libroSeleccionado.setCategoria(categoria.getValue());
            libroSeleccionado.setEstado(estado.getValue());

            // Refrescar la tabla para mostrar los cambios
            tablaLibros.refresh();
        }
    }

    @FXML
    public void eliminarLibro() {
        // Obtener el libro seleccionado en la tabla y eliminarlo
        TablaVista libroSeleccionado = tablaLibros.getSelectionModel().getSelectedItem();
        if (libroSeleccionado != null) {
            listaLibros.remove(libroSeleccionado);
        }
    }

    private void limpiarCampos() {
        idLibro.clear();
        titulo.clear();
        autor.clear();
        fechaPres.clear();
        fechaDev.clear();
        categoria.setValue(null);
        estado.setValue(null);
    }


}