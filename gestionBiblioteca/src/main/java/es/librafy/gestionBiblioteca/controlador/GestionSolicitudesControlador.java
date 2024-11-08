package es.librafy.gestionBiblioteca.controlador;

import es.librafy.gestionBiblioteca.modelo.Solicitudes;
import es.librafy.gestionBiblioteca.modelo.SolicitudesCRUD;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;

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

    // Lista observable para almacenar los datos
    private ObservableList<Solicitudes> listaSolicitudes;

    // Método para inicializar la tabla y cargar los datos
    public void initialize() {
        // Configuración de las columnas de la tabla
        colId.setCellValueFactory(new PropertyValueFactory<>("id_solicitud"));
        colUsuario.setCellValueFactory(new PropertyValueFactory<>("usuario_id"));
        colLibro.setCellValueFactory(new PropertyValueFactory<>("libro_id"));
        colModoEntrega.setCellValueFactory(new PropertyValueFactory<>("modo_entrega"));
        colDisponibilidad.setCellValueFactory(new PropertyValueFactory<>("disponibilidad"));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));

        // Cargar los datos en la tabla
        cargarDatos();
    }

    // Método para cargar datos desde la base de datos
    private void cargarDatos() {
        listaSolicitudes = FXCollections.observableArrayList();

        SolicitudesCRUD solicitudesCRUD = new SolicitudesCRUD();
        try {
            listaSolicitudes.addAll(solicitudesCRUD.obtenerTodasLasSolicitudes());
            tablaSolicitudes.setItems(listaSolicitudes);
        } catch (SQLException e) {
            e.printStackTrace();
            // Manejo de errores
        }
    }
}


