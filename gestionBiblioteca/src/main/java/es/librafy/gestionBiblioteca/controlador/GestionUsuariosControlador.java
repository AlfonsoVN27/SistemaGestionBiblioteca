package es.librafy.gestionBiblioteca.controlador;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.IntegerStringConverter;
import javafx.scene.input.MouseEvent;
import javafx.event.ActionEvent;
import es.librafy.gestionBiblioteca.modelo.*;

public class GestionUsuariosControlador {
    
    @FXML
    private TextField id;
    @FXML
    private TextField nombre;
    @FXML
    private TextField f_nac;
    @FXML
    private TextField email;
    @FXML
    private TextField telf;
    @FXML
    private TextField dni;
    @FXML
    private TextField contrasena;
    @FXML
    private TableView<Usuarios> tablaUsuarios;
    @FXML
    private TableColumn<Usuarios, Integer> columnaId;
    @FXML
    private TableColumn<Usuarios, String> columnaNombre;
    @FXML
    private TableColumn<Usuarios, String> columnaFnac;
    @FXML
    private TableColumn<Usuarios, String> columnaEmail;
    @FXML
    private TableColumn<Usuarios, String> columnaTelf;
    @FXML
    private TableColumn<Usuarios, String> columnaDni;
    @FXML
    private Button btnAgregar;
    @FXML
    private Button btnModificar;
    @FXML
    private Button btnEliminar;
    
    private ObservableList<Usuarios> listaUsuarios = FXCollections.observableArrayList();
    
    @FXML
    public void initialize() {
        tablaUsuarios.setEditable(true);

        // Configurar columnas como editables
        columnaId.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getId_usuario()));
        columnaId.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        columnaId.setOnEditCommit(event -> event.getRowValue().setId_usuario(event.getNewValue()));

        columnaNombre.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getNombre()));
        columnaNombre.setCellFactory(TextFieldTableCell.forTableColumn());
        columnaNombre.setOnEditCommit(event -> event.getRowValue().setNombre(event.getNewValue()));

        columnaFnac.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getFecha_nacimiento()));
        columnaFnac.setCellFactory(TextFieldTableCell.forTableColumn());
        columnaFnac.setOnEditCommit(event -> event.getRowValue().setFecha_nacimiento(event.getNewValue()));

        columnaEmail.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getEmail()));
        columnaEmail.setCellFactory(TextFieldTableCell.forTableColumn());
        columnaEmail.setOnEditCommit(event -> event.getRowValue().setEmail(event.getNewValue()));

        columnaTelf.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getNum_telefono()));
        columnaTelf.setCellFactory(TextFieldTableCell.forTableColumn());
        columnaTelf.setOnEditCommit(event -> event.getRowValue().setNum_telefono(event.getNewValue()));

        columnaDni.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getDni()));
        columnaDni.setCellFactory(TextFieldTableCell.forTableColumn());
        columnaDni.setOnEditCommit(event -> event.getRowValue().setDni(event.getNewValue()));

        // Esto deshabilita botones de modificar y eliminar si no hay selección
        btnModificar.setDisable(true);
        btnEliminar.setDisable(true);
        
        tablaUsuarios.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            boolean seleccionada = newSelection != null;
            btnModificar.setDisable(!seleccionada);
            btnEliminar.setDisable(!seleccionada);
        });

        tablaUsuarios.setItems(listaUsuarios);
    }
   
    @FXML
    public void agregarUsuario() {
        try {
            int idInt = Integer.parseInt(id.getText());
            Usuarios nuevoUsuario = new Usuarios(idInt, nombre.getText(), f_nac.getText(), email.getText(), telf.getText(), dni.getText(), contrasena.getText());
            listaUsuarios.add(nuevoUsuario);
            id.clear(); nombre.clear(); f_nac.clear(); email.clear(); telf.clear(); dni.clear(); contrasena.clear();
        } catch (NumberFormatException e) {
            System.out.println("El ID debe ser un número entero válido.");
        }
    }

    @FXML
    public void modificar(ActionEvent event) {
        Usuarios usuarioSeleccionado = tablaUsuarios.getSelectionModel().getSelectedItem();
        if (usuarioSeleccionado != null) {
            usuarioSeleccionado.setId_usuario(Integer.parseInt(id.getText()));
            usuarioSeleccionado.setNombre(nombre.getText());
            usuarioSeleccionado.setFecha_nacimiento(f_nac.getText());
            usuarioSeleccionado.setEmail(email.getText());
            usuarioSeleccionado.setNum_telefono(telf.getText());
            usuarioSeleccionado.setDni(dni.getText());
            usuarioSeleccionado.setContrasena(contrasena.getText());
            tablaUsuarios.refresh();
        }
    }

    @FXML
    public void eliminar(ActionEvent action) {
        Usuarios usuarioSeleccionado = tablaUsuarios.getSelectionModel().getSelectedItem();
        if (usuarioSeleccionado != null) {
            listaUsuarios.remove(usuarioSeleccionado);
            tablaUsuarios.refresh();
        }
    }
}