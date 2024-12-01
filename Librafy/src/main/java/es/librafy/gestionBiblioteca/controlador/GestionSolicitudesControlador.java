package es.librafy.gestionBiblioteca.controlador;

import es.librafy.gestionBiblioteca.modelo.Libros;
import es.librafy.gestionBiblioteca.modelo.Solicitudes;
import es.librafy.gestionBiblioteca.modelo.SolicitudesCRUD;
import es.librafy.gestionBiblioteca.util.DatabaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class GestionSolicitudesControlador {

    @FXML
    private TableView<Solicitudes> tablaSolicitudes;
    @FXML
    private TableColumn<Solicitudes, Integer> colId;
    @FXML
    private TableColumn<Solicitudes, Integer> colUsuario;
    @FXML
    private TableColumn<Solicitudes, Integer> colLibro;
    @FXML
    private TableColumn<Solicitudes, String> colModoEntrega;
    @FXML
    private TableColumn<Solicitudes, String> colDisponibilidad;
    @FXML
    private TableColumn<Solicitudes, String> colEstado;
    @FXML
    private ComboBox<String> cb_disponibilidad;
    @FXML
    private ComboBox<String> cb_estado;
    @FXML
    private Button btn_modificar;
    
    @FXML
    private TextField txt_busqueda;
    
    @FXML
    private Button btn_volver;

    private ObservableList<Solicitudes> listaSolicitudes;

    // Método para inicializar la tabla, ComboBoxes y cargar los datos
    public void initialize() {
    	System.out.println("Método initialize ejecutado");

        // Configuración de las columnas de la tabla
        colId.setCellValueFactory(new PropertyValueFactory<>("id_solicitud"));
        colUsuario.setCellValueFactory(new PropertyValueFactory<>("usuario_id"));
        colLibro.setCellValueFactory(new PropertyValueFactory<>("libro_id"));
        colModoEntrega.setCellValueFactory(new PropertyValueFactory<>("modo_entrega"));
        colDisponibilidad.setCellValueFactory(new PropertyValueFactory<>("disponibilidad"));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));

        // Inicializar los ComboBox
        cb_disponibilidad.setItems(FXCollections.observableArrayList("Disponible", "En préstamo"));
        cb_estado.setItems(FXCollections.observableArrayList("Pendiente", "Rechazado", "Aprobado"));

        // Cargar los datos en la tabla
        cargarDatos();

        // Configurar la acción para el botón Modificar
        btn_modificar.setOnAction(event -> modificarSolicitudes());
    }
    
    public void realizarBusqueda() {
        String textoBusqueda = txt_busqueda.getText().toLowerCase(); // Obtener el texto y pasarlo a minúsculas

        ObservableList<Solicitudes> solicitudesFiltradas = FXCollections.observableArrayList();

        for (Solicitudes solicitud : listaSolicitudes) {
            // Verifica si el texto de búsqueda está contenido en algún campo de la solicitud
            if (String.valueOf(solicitud.getId_solicitud()).contains(textoBusqueda) ||
                String.valueOf(solicitud.getUsuario_id()).contains(textoBusqueda) ||
                String.valueOf(solicitud.getLibro_id()).contains(textoBusqueda) ||
                solicitud.getModo_entrega().toLowerCase().contains(textoBusqueda) ||
                solicitud.getDisponibilidad().toLowerCase().contains(textoBusqueda) ||
                solicitud.getEstado().toLowerCase().contains(textoBusqueda)) {
                solicitudesFiltradas.add(solicitud); // Si coincide, añadirla a la lista filtrada
            }
        }

        // Actualiza la tabla con las solicitudes filtradas
        tablaSolicitudes.setItems(solicitudesFiltradas);
        tablaSolicitudes.refresh(); // Refrescar la tabla para mostrar los resultados filtrados
    }

    public void volver() {
        // Obtener el Stage actual
        Stage stage = (Stage) btn_volver.getScene().getWindow();
        stage.close();  // Cerrar la ventana actual

        // Si quieres cargar otra ventana (por ejemplo, la ventana principal)
        try {
            // Cargar la vista anterior (modifica la ruta según tu estructura de archivos)
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/es/librafy/gestionBiblioteca/vista/AdminPrincipal.fxml"));
            Parent root = loader.load();
            Stage newStage = new Stage();
            newStage.setScene(new Scene(root));
            newStage.show();  // Mostrar la ventana anterior
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error al intentar abrir la ventana anterior.");
        }
    }
    
    // Método para cargar datos desde la base de datos
    private void cargarDatos() {
        listaSolicitudes = FXCollections.observableArrayList();
        SolicitudesCRUD solicitudesCRUD = new SolicitudesCRUD();
        try {
            listaSolicitudes.addAll(solicitudesCRUD.obtenerTodasLasSolicitudes());
            System.out.println("Datos cargados: " + listaSolicitudes.size());
            
            // Asigna la lista a la tabla
            tablaSolicitudes.setItems(listaSolicitudes);
            tablaSolicitudes.refresh(); // Refrescar la tabla
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error al cargar los datos desde la base de datos.");
        }
    }


 // Método para eliminar la solicitud seleccionada en la tabla
    public void eliminarSolicitud() {
    	Solicitudes solicitudSeleccionada = tablaSolicitudes.getSelectionModel().getSelectedItem();
        
        if (solicitudSeleccionada != null) {
            // Confirmar eliminación con el usuario
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmar eliminación");
            alert.setHeaderText(null);
            alert.setContentText("¿Está seguro de que desea eliminar esta solicitud?");
            
            // Si el usuario confirma la eliminación
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    // Crear instancia de SolicitudesCRUD para manejar la eliminación
                    SolicitudesCRUD solicitudesCRUD = new SolicitudesCRUD();
                    try {
                        // Eliminar la solicitud de la base de datos
                        solicitudesCRUD.deleteSolicitud(solicitudSeleccionada.getId_solicitud());

                        // Eliminar la solicitud de la lista observable
                        listaSolicitudes.remove(solicitudSeleccionada);

                        // Refrescar la tabla para reflejar los cambios
                        tablaSolicitudes.refresh();

                        // Mostrar mensaje informativo de éxito
                        Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);
                        infoAlert.setTitle("Eliminado");
                        infoAlert.setHeaderText(null);
                        infoAlert.setContentText("La solicitud ha sido eliminada correctamente.");
                        infoAlert.showAndWait();
                    } catch (SQLException e) {
                        e.printStackTrace();

                        // Mostrar mensaje de error si la eliminación falla
                        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                        errorAlert.setTitle("Error al eliminar");
                        errorAlert.setHeaderText(null);
                        errorAlert.setContentText("Ocurrió un error al intentar eliminar la solicitud en la base de datos.");
                        errorAlert.showAndWait();
                    }
                }
            });
        } else {
            // Si no hay solicitud seleccionada, mostrar un mensaje de error
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Debe seleccionar una solicitud para eliminar.");
            alert.showAndWait();
            
        }
    }


    // Método para modificar la solicitud seleccionada en la tabla
    public void modificarSolicitudes() {
        Solicitudes solicitudSeleccionada = tablaSolicitudes.getSelectionModel().getSelectedItem();
        
        if (solicitudSeleccionada != null) {
            String nuevaDisponibilidad = cb_disponibilidad.getValue();
            String nuevoEstado = cb_estado.getValue();

            if (nuevaDisponibilidad != null && nuevoEstado != null) {
                solicitudSeleccionada.setDisponibilidad(nuevaDisponibilidad);
                solicitudSeleccionada.setEstado(nuevoEstado);

                // Actualizar la base de datos
                SolicitudesCRUD solicitudesCRUD = new SolicitudesCRUD();
                try {
                    solicitudesCRUD.updateSolicitud(solicitudSeleccionada);
                    tablaSolicitudes.refresh(); // Refrescar la tabla para mostrar los cambios
                } catch (SQLException e) {
                    e.printStackTrace();
                    // Mostrar mensaje de error si la actualización falla
                    System.out.println("Error al actualizar la solicitud en la base de datos.");
                }
            }
        }
    }
}

