package es.librafy.gestionBiblioteca.controlador;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import es.librafy.gestionBiblioteca.modelo.Libros;
import es.librafy.gestionBiblioteca.modelo.Solicitudes;
import es.librafy.gestionBiblioteca.modelo.Usuarios;
import es.librafy.gestionBiblioteca.util.DatabaseConnection;

public class GestionUsuariosControlador {

    @FXML
    private TableView<Usuarios> tablaUsuarios;
    @FXML
    private TableColumn<Usuarios, Integer> clId;
    @FXML
    private TableColumn<Usuarios, String> clNombre;
    @FXML
    private TableColumn<Usuarios, String> clDni;
    @FXML
    private TableColumn<Usuarios, String> clFnacimiento;
    @FXML
    private TableColumn<Usuarios, String> clDireccion;
    @FXML
    private TableColumn<Usuarios, String> clNumTelf;
    @FXML
    private TableColumn<Usuarios, String> clEmail;
    @FXML
    private TableColumn<Usuarios, String> clNomUsuario;
    @FXML
    private TableColumn<Usuarios, String> clContrasena;
    
    @FXML
    private TextField txt_busqueda;


    @FXML
    private Button btn_guardarUsuarios;
    @FXML
    private Button btn_volver;
    @FXML
    private Button btn_anadirUsuario;
    @FXML
    private Button btn_modificarUsuario;
    @FXML
    private Button btn_eliminarUsuario;

    private ObservableList<Usuarios> listaUsuarios;

    // Método para inicializar la tabla y cargar los datos
    public void initialize() {
        // Hacer la tabla editable
        tablaUsuarios.setEditable(true);

        // Configuración de las columnas de la tabla
        clId.setCellValueFactory(new PropertyValueFactory<>("id_usuario"));
        clNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        clDni.setCellValueFactory(new PropertyValueFactory<>("dni"));
        clFnacimiento.setCellValueFactory(new PropertyValueFactory<>("fecha_nacimiento"));
        clDireccion.setCellValueFactory(new PropertyValueFactory<>("direccion"));
        clNumTelf.setCellValueFactory(new PropertyValueFactory<>("num_telefono"));
        clNomUsuario.setCellValueFactory(new PropertyValueFactory<>("nomUsuario"));
        clEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        clContrasena.setCellValueFactory(new PropertyValueFactory<>("contrasena"));

        // Configurar las columnas editables
        clNombre.setCellFactory(TextFieldTableCell.forTableColumn());
        clDni.setCellFactory(TextFieldTableCell.forTableColumn());
        clFnacimiento.setCellFactory(TextFieldTableCell.forTableColumn());
        clDireccion.setCellFactory(TextFieldTableCell.forTableColumn());
        clNumTelf.setCellFactory(TextFieldTableCell.forTableColumn());
        clNomUsuario.setCellFactory(TextFieldTableCell.forTableColumn());
        clEmail.setCellFactory(TextFieldTableCell.forTableColumn());
        clContrasena.setCellFactory(TextFieldTableCell.forTableColumn());

        // Eventos para editar las columnas
        clNombre.setOnEditCommit(event -> event.getRowValue().setNombre(event.getNewValue()));
        clDni.setOnEditCommit(event -> event.getRowValue().setDni(event.getNewValue()));
        clFnacimiento.setOnEditCommit(event -> {
            String nuevaFecha = event.getNewValue();
            if (esFechaValida(nuevaFecha)) {
                event.getRowValue().setFecha_nacimiento(nuevaFecha);
            } else {
                mostrarAlerta("Fecha no válida", "La fecha de nacimiento no es válida.");
            }
        });
        clDireccion.setOnEditCommit(event -> event.getRowValue().setDireccion(event.getNewValue()));
        clNumTelf.setOnEditCommit(event -> event.getRowValue().setNum_telefono(event.getNewValue()));
        clNomUsuario.setOnEditCommit(event -> event.getRowValue().setNomUsuario(event.getNewValue()));
        clEmail.setOnEditCommit(event -> event.getRowValue().setEmail(event.getNewValue()));
        clContrasena.setOnEditCommit(event -> event.getRowValue().setContrasena(event.getNewValue()));

        cargarDatos();
    }


    // Método para cargar los datos de los usuarios desde la base de datos
    private void cargarDatos() {
        listaUsuarios = FXCollections.observableArrayList();

        String query = "SELECT * FROM usuarios";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                int id_usuario = rs.getInt("id_usuario");
                String nombre = rs.getString("nombre");
                String dni = rs.getString("dni");
                String fecha_nacimiento = rs.getString("fecha_nacimiento");
                String direccion = rs.getString("direccion");
                String num_telefono = rs.getString("num_telefono");
                String nomUsuario = rs.getString("nomUsuario");
                String email = rs.getString("email");
                String contrasena = rs.getString("contrasena");

                // Asegúrate de agregar correctamente los usuarios a la lista
                listaUsuarios.add(new Usuarios(id_usuario, nombre, dni, fecha_nacimiento, direccion, num_telefono, nomUsuario, email, contrasena));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        tablaUsuarios.setItems(listaUsuarios);
    }

    
    private void actualizarUsuarios() {
        String query = "UPDATE usuarios SET nombre = ?, dni = ?, fecha_nacimiento = ?, direccion = ?, num_telefono = ?, email = ?, nomUsuario = ?, contrasena = ? WHERE id_usuario = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            for (Usuarios usuario : tablaUsuarios.getItems()) {
            	pstmt.setString(1, usuario.getNombre());
                pstmt.setString(2, usuario.getDni());
                pstmt.setString(3, usuario.getFecha_nacimiento());
                pstmt.setString(4, usuario.getDireccion());
                pstmt.setString(5, usuario.getNum_telefono());
                pstmt.setString(6, usuario.getEmail());
                pstmt.setString(7, usuario.getNomUsuario());
                pstmt.setString(8, usuario.getContrasena());
                pstmt.addBatch();
            }

            pstmt.executeBatch();
            System.out.println("Datos actualizados correctamente");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para mostrar alerta de error
    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    // Validación de fecha en formato yyyy-MM-dd
    private boolean esFechaValida(String fecha) {
        try {
            SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
            formato.setLenient(false);
            formato.parse(fecha);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    // Método para añadir un nuevo usuario
    @FXML
    private void añadirUsuario() {
        int nuevoIdUsuario = obtenerNuevoIdUsuario();
        Usuarios nuevoUsuario = new Usuarios(nuevoIdUsuario, "", "", "", "", "", "", "", "");
        listaUsuarios.add(nuevoUsuario);
        tablaUsuarios.refresh();
    }

    // Método para guardar usuarios en la base de datos
    @FXML
    private void guardarUsuarios() {
        actualizarUsuarios();
    }

    // Método para modificar usuarios en la base de datos
    @FXML
    private void modificarUsuarios() {
        String query = "UPDATE usuarios SET nombre = ?, dni = ?, fecha_nacimiento = ?, direccion = ?, num_telefono = ?, email = ?, nomUsuario = ?, contrasena = ? WHERE id_usuario = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            for (Usuarios usuario : listaUsuarios) {
                pstmt.setString(1, usuario.getNombre());
                pstmt.setString(2, usuario.getDni());
                pstmt.setString(3, usuario.getFecha_nacimiento());
                pstmt.setString(4, usuario.getDireccion());
                pstmt.setString(5, usuario.getNum_telefono());
                pstmt.setString(6, usuario.getEmail());
                pstmt.setString(7, usuario.getNomUsuario());
                pstmt.setString(8, usuario.getContrasena());
                pstmt.setInt(9, usuario.getId_usuario()); // Configurar el id_usuario como el noveno parámetro

                pstmt.addBatch();
            }

            pstmt.executeBatch();
            System.out.println("Datos de usuarios actualizados correctamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    public void realizarBusqueda(KeyEvent event) {
        String textoBusqueda = txt_busqueda.getText().toLowerCase();

        // Filtrar los usuarios según el texto de búsqueda
        ObservableList<Usuarios> usuariosFiltrados = FXCollections.observableArrayList();

        for (Usuarios usuario : listaUsuarios) {
            // Filtramos por nombre, dni, o id
            if (usuario.getNombre().toLowerCase().contains(textoBusqueda) ||
                usuario.getDni().toLowerCase().contains(textoBusqueda) ||
                String.valueOf(usuario.getId_usuario()).contains(textoBusqueda)) {
                usuariosFiltrados.add(usuario);
            }
        }

        // Actualizar la tabla con los usuarios filtrados
        tablaUsuarios.setItems(usuariosFiltrados);
    }



    // Método para eliminar un usuario
    @FXML
    private void eliminarUsuario() {
        Usuarios usuarioSeleccionado = tablaUsuarios.getSelectionModel().getSelectedItem();

        if (usuarioSeleccionado != null) {
            // Confirmar eliminación con el usuario
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmar eliminación");
            alert.setHeaderText(null);
            alert.setContentText("¿Está seguro de que desea eliminar este usuario?");
            
            // Si el usuario confirma la eliminación
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    // Obtener el id_usuario del usuario seleccionado
                    int id_usuario = usuarioSeleccionado.getId_usuario();

                    // Eliminar el usuario de la base de datos
                    eliminarUsuarioDeBaseDeDatos(id_usuario);

                    // Eliminar el usuario de la lista en memoria
                    listaUsuarios.remove(usuarioSeleccionado);
                    tablaUsuarios.setItems(listaUsuarios); // Actualizar la tabla

                    // Opcional: Mostrar un mensaje informando de la eliminación
                    Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);
                    infoAlert.setTitle("Eliminado");
                    infoAlert.setHeaderText(null);
                    infoAlert.setContentText("El usuario ha sido eliminado correctamente.");
                    infoAlert.showAndWait();
                }
            });
        } else {
            // Si no hay usuario seleccionado, mostrar un mensaje de error
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Debe seleccionar un usuario para eliminar.");
            alert.showAndWait();
        }
    }

    private void eliminarUsuarioDeBaseDeDatos(int id_usuario) {
        String query = "DELETE FROM usuarios WHERE id_usuario = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, id_usuario);
            pstmt.executeUpdate();
            System.out.println("Usuario eliminado correctamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


  
    
    /*
 // Verificar si hay un libro seleccionado
    if (usuarioSeleccionado != null) {
        // Confirmar eliminación con el usuario
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar eliminación");
        alert.setHeaderText(null);
        alert.setContentText("¿Está seguro de que desea eliminar este libro?");
        
        // Si el usuario confirma la eliminación
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                // Eliminar el libro de la base de datos
            	eliminarUsuarioDeBaseDeDatos(usuarioSeleccionado);

                // Eliminar el libro de la lista en memoria
                listaUsuarios.remove(usuarioSeleccionado);
                tablaUsuarios.setItems(listaUsuarios); // Actualizar la tabla

                // Opcional: Mostrar un mensaje informando de la eliminación
                Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);
                infoAlert.setTitle("Eliminado");
                infoAlert.setHeaderText(null);
                infoAlert.setContentText("El libro ha sido eliminado correctamente.");
                infoAlert.showAndWait();
            }
        });
    } else {
        // Si no hay libro seleccionado, mostrar un mensaje de error
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText("Debe seleccionar un libro para eliminar.");
        alert.showAndWait();
    }
    */
    

    @FXML
    private void volver() {
        Stage stage = (Stage) btn_volver.getScene().getWindow();
        stage.close();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/es/librafy/gestionBiblioteca/vista/AdminPrincipal.fxml"));
            Parent root = loader.load();
            Stage newStage = new Stage();
            newStage.setScene(new Scene(root));
            newStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error al intentar abrir la ventana anterior.");
        }
    }

    private int obtenerNuevoIdUsuario() {
        int nuevoId = 1;
        String query = "SELECT MAX(id_usuario) FROM usuarios";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            if (rs.next()) {
                nuevoId = rs.getInt(1) + 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return nuevoId;
    }
}

