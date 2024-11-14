package es.librafy.gestionBiblioteca.controlador;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import es.librafy.gestionBiblioteca.modelo.Libros;
import es.librafy.gestionBiblioteca.util.DatabaseConnection;
import es.librafy.gestionBiblioteca.util.SesionUsuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class FichaLibroControlador {

    @FXML
    private ImageView imageView;
    @FXML
    private Label lblTitulo;
    @FXML
    private Label lblAutor;
    @FXML
    private Label lblCategoria;
    @FXML
    private Text resumenText;
    @FXML
    private Label lblTitulo2;
    @FXML
	private Label lblEstado;
    @FXML
	private Button btn_cuenta;
	@FXML
	private Button btn_buscar;
	@FXML
	private Button btn_volver;
	@FXML
	private TextField txt_busqueda;
	
    // Este método se llama cuando se carga la ventana de la ficha del libro
    @FXML
    public void initialize(Libros libro) {
    	
    	String estado = libro.getEstado();  // Suponiendo que el estado del libro está en la variable 'estado'

    	
    	btn_buscar.setOnAction(e -> buscarLibros());
        txt_busqueda.setOnAction(e -> buscarLibros());
        btn_cuenta.setOnAction(e -> abrirCuenta());
        btn_volver.setOnAction(e -> volver());
    	
        // Cargar la imagen
        imageView.setImage(new Image(getClass().getResourceAsStream("/imagenes/" + libro.getRutaImagen())));
        
        // Mostrar el título, autor y categoría
        lblTitulo.setText(libro.getTitulo());
        lblAutor.setText(libro.getAutor());
        if ("DEVUELTO".equals(estado)) {
            lblEstado.setText("Disponible");
            // Verde fluorescente más brillante
            lblEstado.setTextFill(javafx.scene.paint.Color.web("#14AE5C"));  // Color verde fluorescente
        } else if ("EN PRÉSTAMO".equals(estado) || "CON RETRASO".equals(estado)) {
            lblEstado.setText("No Disponible");
            lblEstado.setTextFill(javafx.scene.paint.Color.RED);  // Color rojo
        } else {
            lblEstado.setText("Estado desconocido");
            lblEstado.setTextFill(javafx.scene.paint.Color.GRAY);  // Color gris para un estado no definido
        }
        lblCategoria.setText(libro.getCategoria());
        lblTitulo2.setText(libro.getTitulo());
        
        // Mostrar el resumen del libro (puedes obtener un resumen desde la base de datos si lo tienes)
        resumenText.setText(libro.getResumen());
    }
    
    
    
    
    
    
    
    
    @FXML
    public void abrirNovedades(ActionEvent event) {
        try {
            // Cargar el archivo FXML de novedades
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/es/librafy/gestionBiblioteca/vista/Novedades.fxml"));
            Parent root = loader.load();

            // Crear la nueva ventana de novedades
            Stage stage = new Stage();
            stage.setTitle("Novedades");

            // Eliminar la barra de título (minimizar, maximizar, cerrar)
            stage.initStyle(StageStyle.UNDECORATED); // Esto quita la barra de título

            // Establecer la escena
            Scene scene = new Scene(root);
            stage.setScene(scene);

            // Definir la posición personalizada para la ventana de novedades
            // Establecer la posición en la pantalla (ajustado más a la izquierda y arriba)
            double x = 510;  // Desplazamiento horizontal (ajústalo a tu gusto)
            double y = 100;  // Desplazamiento vertical (ajústalo a tu gusto)

            // Establecer la nueva posición de la ventana de novedades
            stage.setX(x);  // Establece la posición X
            stage.setY(y);  // Establece la posición Y

            // Mostrar la ventana de novedades
            stage.show();

            // En este punto, no aplicamos el grisáceo aquí, ya que el botón de cerrar lo va a manejar.
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error al abrir la interfaz de novedades.");
        }
    }
    
    public void abrirCuenta() {
        try {
            // Cargar la vista de Mi Cuenta
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/es/librafy/gestionBiblioteca/vista/Cuenta.fxml"));
            Scene sceneMiCuenta = new Scene(loader.load());

            // Obtener el controlador de la vista de Mi Cuenta
            CuentaControlador cuentaControlador = loader.getController();

            // Pasar el nombre de usuario o ID del usuario al controlador de Mi Cuenta
            String usuarioActual = SesionUsuario.getUsuarioActual();  // Obtener el usuario de la sesión
            cuentaControlador.setUsuarioActual(usuarioActual); // Pasamos el usuario actual

            // Crear y mostrar la nueva ventana de Cuenta
            Stage stageMiCuenta = new Stage();
            stageMiCuenta.setScene(sceneMiCuenta);
            stageMiCuenta.setTitle("Mi Cuenta");
            stageMiCuenta.show();

            // Cerrar la ventana actual (Interfaz Principal)
            Stage currentStage = (Stage) btn_cuenta.getScene().getWindow();
            currentStage.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }
    
    @FXML
    private void volver() {
        Stage stage = (Stage) btn_volver.getScene().getWindow();
        stage.close();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/es/librafy/gestionBiblioteca/vista/ResultadosBusqueda.fxml"));
            Parent root = loader.load();
            Stage newStage = new Stage();
            newStage.setScene(new Scene(root));
            newStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error al intentar abrir la ventana anterior.");
        }
    }
    
    public void buscarLibros() {
        String criterio = txt_busqueda.getText().trim();
        if (criterio.isEmpty()) {
            mostrarMensaje("Por favor, introduce un término de búsqueda.");
            return;
        }

        List<Libros> resultados = new ArrayList<>();
        String query = "SELECT * FROM libros WHERE titulo LIKE ? OR autor LIKE ? OR categoria LIKE ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            String searchPattern = "%" + criterio + "%";
            stmt.setString(1, searchPattern);
            stmt.setString(2, searchPattern);
            stmt.setString(3, searchPattern);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int id_libro = rs.getInt("id_libro");
                    String titulo = rs.getString("titulo");
                    String autor = rs.getString("autor");
                    String fecha_prestamo = rs.getString("fecha_prestamo");
                    String fecha_devolucion = rs.getString("fecha_devolucion");
                    String categoria = rs.getString("categoria");
                    String estado = rs.getString("estado");
                    String rutaImagen = rs.getString("rutaImagen");
                    String resumen = rs.getString("resumen");

                    Libros libro = new Libros(id_libro, titulo, autor, fecha_prestamo, fecha_devolucion, categoria, estado, rutaImagen, resumen);
                    resultados.add(libro);
                }
            }
            
            SesionUsuario.setResultadosBusqueda(resultados);

            mostrarResultadosEnNuevaVentana(resultados);

        } catch (SQLException e) {
            e.printStackTrace();
            mostrarMensaje("Error en la conexión a la base de datos.");
        }
    }

    private void mostrarResultadosEnNuevaVentana(List<Libros> resultados) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/es/librafy/gestionBiblioteca/vista/ResultadosBusqueda.fxml"));
            Parent root = loader.load();

            ResultadosBusquedaControlador resultadosControlador = loader.getController();
            resultadosControlador.mostrarResultados(resultados);

            // Cambia a AnchorPane para la nueva ventana
            Stage stage = new Stage();
            stage.setTitle("Resultados de la Búsqueda");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
            mostrarMensaje("No se pudo cargar la interfaz de resultados.");
        }
    }
}
