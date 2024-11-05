package es.librafy.gestionBiblioteca.controlador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import es.librafy.gestionBiblioteca.modelo.Libros;
import es.librafy.gestionBiblioteca.util.DatabaseConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class InterfazPrincipalControlador {
	
	@FXML
    private TilePane tilePaneCategorias;
	
	@FXML
	private TextField txt_busqueda;
	
	@FXML
	private Button btn_buscar;

    @FXML
    public void initialize() {
        cargarCategorias();
        
        btn_buscar.setOnAction(e -> buscarLibros());
        txt_busqueda.setOnAction(e -> buscarLibros());
    }
    
    @FXML
    public void buscarLibros(ActionEvent event) {
        // Your logic for searching books
    }

    private void cargarCategorias() {
        // Lista de categorías y la misma imagen para cada una
        String[] categorias = {"Ficción", "No Ficción", "Infantil", "Inglés", "Misterio y Suspenso"};
        String[] rutaImagen = {"/imagenes/ficcion.jpg", "/imagenes/no_ficcion.jpg", "/imagenes/infantil.jpg", "/imagenes/ingles.jpg", "/imagenes/misterio.jpg"}; 

        // Generar cada tarjeta de categoría y añadirla al TilePane
        for (int i = 0; i < categorias.length; i++) {
            tilePaneCategorias.getChildren().add(crearTarjetaCategoria(categorias[i], rutaImagen[i]));
        }
    }

    private StackPane crearTarjetaCategoria(String nombreCategoria, String rutaImagen) {
        // Crear la tarjeta como StackPane
        StackPane tarjeta = new StackPane();

        // Imagen de fondo
        ImageView imageView = new ImageView(new Image(getClass().getResourceAsStream(rutaImagen)));
        imageView.setFitWidth(150);  // Ajusta el tamaño según lo que necesites
        imageView.setFitHeight(150);

        // Etiqueta de nombre
        Label label = new Label(nombreCategoria);
        label.setStyle("-fx-font-size: 16px; -fx-text-fill: white; -fx-background-color: rgba(0, 0, 0, 0.6);");
        label.setMaxWidth(Double.MAX_VALUE);
        StackPane.setAlignment(label, javafx.geometry.Pos.BOTTOM_CENTER);

        // Efecto de sombra para el hover
        DropShadow sombra = new DropShadow(10, Color.DARKGRAY);
        tarjeta.setOnMouseEntered(e -> imageView.setEffect(sombra));
        tarjeta.setOnMouseExited(e -> imageView.setEffect(null));

        tarjeta.getChildren().addAll(imageView, label);
        return tarjeta;
    }
    
    private void buscarLibros() {
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

                    Libros libro = new Libros(id_libro, titulo, autor, fecha_prestamo, fecha_devolucion, categoria, estado);
                    resultados.add(libro);
                }
            }

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

    private void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }
    
    
}
