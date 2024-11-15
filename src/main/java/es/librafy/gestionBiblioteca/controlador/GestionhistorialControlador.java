package es.librafy.gestionBiblioteca.controlador;

import es.librafy.gestionBiblioteca.modelo.TablaVista;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class GestionhistorialControlador {

    @FXML
    private Button btnDevolver;
    @FXML
    private Button btnRenovar;
    @FXML
    private Button btn_volver;
    @FXML
    private ComboBox<String> estado;
    @FXML
    private TableView<TablaVista> tablaHistorial;
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

    private ObservableList<TablaVista> listaHistorial = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        tituloColumn.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        autorColumn.setCellValueFactory(new PropertyValueFactory<>("autor"));
        fechapresColumn.setCellValueFactory(new PropertyValueFactory<>("fecha prestamo"));
        fechadelColumn.setCellValueFactory(new PropertyValueFactory<>("fecha devolución"));
        categoriaColumn.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        estadoColumn.setCellValueFactory(new PropertyValueFactory<>("estado"));
        
        estado.setItems(FXCollections.observableArrayList("Devuelto", "En prestamo", "Con Retraso"));
        
        tablaHistorial.setItems(listaHistorial);

        btnDevolver.setOnAction(event -> devolverLibro());
        btnRenovar.setOnAction(event -> renovarLibro());
    }

    @FXML
    private void devolverLibro() {
        TablaVista libroSeleccionado = tablaHistorial.getSelectionModel().getSelectedItem();
        if (libroSeleccionado != null) {
            libroSeleccionado.setEstado("Devuelto");
            tablaHistorial.refresh();
        }
    }

    @FXML
    private void renovarLibro() {
        TablaVista libroSeleccionado = tablaHistorial.getSelectionModel().getSelectedItem();
        if (libroSeleccionado != null) {
            libroSeleccionado.setEstado("En prestamo");
            tablaHistorial.refresh();
        }
    }
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
}

