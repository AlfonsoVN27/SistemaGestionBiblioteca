package es.librafy.gestionBiblioteca.controlador;

import es.librafy.gestionBiblioteca.modelo.Historial;
import es.librafy.gestionBiblioteca.util.DatabaseConnection;
import es.librafy.gestionBiblioteca.util.SesionUsuario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Random;

public class HistorialControlador {

    @FXML
    private TableView<Historial> tablaHistorial;

    @FXML
    private TableColumn<Historial, Integer> colId;

    @FXML
    private TableColumn<Historial, Integer> colUsuario;

    @FXML
    private TableColumn<Historial, String> colTitulo;

    @FXML
    private TableColumn<Historial, String> colFPrestamo;

    @FXML
    private TableColumn<Historial, String> colFDevolucion;

    @FXML
    private TableColumn<Historial, String> colEstado;

    private ObservableList<Historial> historialList;
    
    @FXML
    private Button btn_volver;
    
    private String usuarioActual;

    public void setUsuarioActual(String usuario) {
        this.usuarioActual = usuario;
        cargarHistorialDelUsuario();
    }

    private void cargarHistorialDelUsuario() {
    	String query = "SELECT id_historial, usuario_id, titulo, fecha_prestamo, fecha_devolucion, estado FROM historial WHERE usuario_id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            // Usa el usuarioActual para filtrar los registros
            statement.setString(1, usuarioActual);

            ResultSet resultSet = statement.executeQuery();

            // Aquí debes poblar la tabla de la vista del historial
            while (resultSet.next()) {
                // Lógica para añadir registros a la tabla del historial
                System.out.println("Historial: " + resultSet.getString("titulo")); // Ejemplo de depuración
            }

        } catch (SQLException e) {
            e.printStackTrace();
            // Manejo de errores
        }
    }

    @FXML
    public void initialize() {
        // Configurar las columnas del TableView
    	colId.setCellValueFactory(new PropertyValueFactory<>("id_historial"));
    	colUsuario.setCellValueFactory(new PropertyValueFactory<>("usuario_id"));
    	colTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
    	colFPrestamo.setCellValueFactory(new PropertyValueFactory<>("fecha_prestamo"));
    	colFDevolucion.setCellValueFactory(new PropertyValueFactory<>("fecha_devolucion"));
    	colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));

        // Cargar los datos desde la base de datos
        cargarHistorial();
    }
    
    @FXML
    private void cargarHistorial() {
        historialList = FXCollections.observableArrayList();

        String usuarioActual = SesionUsuario.getUsuarioActual();
        if (usuarioActual == null) {
            System.out.println("No hay usuario en sesión.");
            return;
        }

        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT id_historial, usuario_id, titulo, fecha_prestamo, fecha_devolucion, estado " +
                           "FROM historial WHERE usuario_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, usuarioActual);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                historialList.add(new Historial(
                    resultSet.getInt("id_historial"),
                    resultSet.getInt("usuario_id"),
                    resultSet.getString("titulo"),
                    resultSet.getString("fecha_prestamo"),
                    resultSet.getString("fecha_devolucion"),
                    resultSet.getString("estado")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Asignar los datos al TableView
        tablaHistorial.setItems(historialList);
    }


    
    private int generarIdUnico() {
        Random random = new Random();
        return random.nextInt(10000) + 1; // Genera un ID aleatorio entre 1 y 10000
    }

    // Método para generar una fecha aleatoria dentro del último año
    private LocalDate generarFechaAleatoria() {
        Random random = new Random();
        LocalDate hoy = LocalDate.now();
        return hoy.minusDays(random.nextInt(365)); // Restar hasta 365 días para fechas aleatorias dentro del año
    }
    
    @FXML
    private void volver() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/es/librafy/gestionBiblioteca/vista/Cuenta.fxml"));
            AnchorPane root = loader.load();

            // Crear una nueva ventana
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

            // Cerrar la ventana actual
            Stage currentStage = (Stage) btn_volver.getScene().getWindow();
            currentStage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

