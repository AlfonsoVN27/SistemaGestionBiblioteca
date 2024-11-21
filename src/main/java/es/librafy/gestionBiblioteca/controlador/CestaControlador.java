package es.librafy.gestionBiblioteca.controlador;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import es.librafy.gestionBiblioteca.modelo.Libros;
import es.librafy.gestionBiblioteca.util.CestaLibro;
import es.librafy.gestionBiblioteca.util.DatabaseConnection;
import es.librafy.gestionBiblioteca.util.SesionUsuario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class CestaControlador {

	@FXML
	private VBox contenedorLibros; // Contenedor donde se agregarán los libros

    // Este método se llama cuando se carga la ventana de la cesta
    @FXML
    public void initialize() {
        // Cargar los libros de la cesta
        cargarLibrosEnCesta();
        contenedorLibrosScroll.setMaxWidth(Region.USE_COMPUTED_SIZE);
    }
    
    private ObservableList<CestaLibro> librosEnCesta = FXCollections.observableArrayList();

    // Este método se llama cuando se cargan los datos en la ventana
    public void setLibrosEnCesta(List<Libros> libros) {
        this.librosEnCesta.clear();  // Limpiar cualquier dato anterior
        for (Libros libro : libros) {
            agregarLibroACesta(libro);  // Añadir el libro con cantidad 1 si es nuevo
        }
        cargarLibrosEnCesta();  // Actualizar la interfaz
    }
    
    private void agregarLibroACesta(Libros libro) {
        for (CestaLibro cestaLibro : librosEnCesta) {
            if (cestaLibro.getLibro().getTitulo().equals(libro.getTitulo())) {
                cestaLibro.incrementarCantidad();  // Si el libro ya está en la cesta, aumentar la cantidad
                return;
            }
        }
        // Si el libro no está en la cesta, lo añadimos
        librosEnCesta.add(new CestaLibro(libro));
    }


    // Cargar los libros en la cesta desde la sesión del usuario
    
    @FXML
    private VBox contenedorLibrosScroll;

    @FXML
    private void cargarLibrosEnCesta() {
        contenedorLibrosScroll.getChildren().clear(); // Limpiar el contenedor antes de cargar los libros

        // Crear un VBox para contener todos los libros
        VBox librosVBox = new VBox(10); // Espaciado de 10px entre los libros
        librosVBox.setPadding(new Insets(10)); // Añadir padding al VBox

        for (CestaLibro cestaLibro : librosEnCesta) {
            Libros libro = cestaLibro.getLibro();  // Obtener el libro de la cesta

            // Crear un VBox para el contenido de cada libro
            VBox libroVBox = new VBox(10); // Espaciado entre los elementos del libro
            libroVBox.setStyle("-fx-padding: 10; -fx-border-color: gray; -fx-border-width: 1px;");

            // Primera parte: Imagen del libro
            HBox imagenBox = new HBox(10);
            ImageView imageView = new ImageView();
            String rutaImagen = libro.getRutaImagen();

            if (rutaImagen != null && !rutaImagen.isEmpty()) {
                InputStream inputStream = getClass().getResourceAsStream("/imagenes/" + rutaImagen);
                if (inputStream != null) {
                    Image image = new Image(inputStream);
                    imageView.setImage(image);
                    imageView.setFitWidth(100);
                    imageView.setPreserveRatio(true);
                } else {
                    System.out.println("Error: Imagen no encontrada en la ruta /imagenes/" + rutaImagen);
                    imageView.setImage(new Image("/imagenes/imagen_predeterminada.png"));
                    imageView.setFitWidth(100);
                    imageView.setPreserveRatio(true);
                }
            } else {
                System.out.println("Advertencia: Ruta de imagen vacía o nula para el libro " + libro.getTitulo());
                imageView.setImage(new Image("/imagenes/imagen_predeterminada.png"));
                imageView.setFitWidth(100);
                imageView.setPreserveRatio(true);
            }
            imagenBox.getChildren().add(imageView); // Añadir la imagen al HBox

            // Segunda parte: Título, Autor, Categoría
            VBox textoBox = new VBox(10); // Espaciado vertical entre el título y el autor (10px)
            Text titulo = new Text(libro.getTitulo());
            titulo.setStyle("-fx-font-weight: bold; -fx-font-size: 18px;");
            
            Text autor = new Text(libro.getAutor());
            autor.setStyle("-fx-font-size: 16px;");
            
            Text categoria = new Text(libro.getCategoria());
            categoria.setStyle("-fx-font-size: 16px;");

            textoBox.getChildren().addAll(titulo, autor, categoria);

            // Añadir imagen y texto al VBox del libro
            libroVBox.getChildren().addAll(imagenBox, textoBox);

            // Tercera parte: Botón "Eliminar" alineado a la derecha
            HBox hboxEliminar = new HBox();
            Button btnEliminar = new Button("Eliminar");
            btnEliminar.setOnAction(e -> eliminarDeCesta(cestaLibro));
            hboxEliminar.getChildren().add(btnEliminar);
            hboxEliminar.setAlignment(Pos.CENTER_RIGHT);  // Alinear el botón a la derecha
            libroVBox.getChildren().add(hboxEliminar);  // Añadir el HBox con el botón al VBox del libro

            // Añadir el VBox con todo el contenido del libro al VBox principal
            librosVBox.getChildren().add(libroVBox);
        }

        // Asignar el VBox con los libros al contenedor del ScrollPane
        contenedorLibrosScroll.getChildren().add(librosVBox);
    }



   
    
    


    

    private void eliminarDeCesta(CestaLibro cestaLibro) {
        librosEnCesta.remove(cestaLibro);  // Eliminar el libro de la cesta
        cargarLibrosEnCesta();  // Actualizar la vista
    }
    
    private void crearSolicitud(int libroId, String modoEntrega) {
        try {
            // Obtener el usuario actual desde SesionUsuario
            String usuarioActual = SesionUsuario.getUsuarioActual();
            if (usuarioActual == null || usuarioActual.isEmpty()) {
                System.err.println("Error: Usuario no autenticado.");
                return;
            }

            // Recuperar el ID del usuario desde la base de datos
            int usuarioId = obtenerUsuarioIdPorNombre(usuarioActual);
            if (usuarioId == -1) {
                System.err.println("Error: No se pudo encontrar el ID del usuario para: " + usuarioActual);
                return;
            }

            // Estado inicial de la solicitud
            String estadoSolicitud = "pendiente";

            // Determinar la disponibilidad del libro según su estado en la base de datos
            String disponibilidad = obtenerDisponibilidadDesdeEstado(libroId);
            if (disponibilidad == null) {
                System.err.println("Error: No se pudo determinar la disponibilidad del libro ID: " + libroId);
                return;
            }

            // Lógica para insertar en la base de datos
            String sql = "INSERT INTO solicitudes (usuario_id, libro_id, modo_entrega, disponibilidad, estado) " +
                         "VALUES (?, ?, ?, ?, ?)";

            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {

                pstmt.setInt(1, usuarioId);
                pstmt.setInt(2, libroId);
                pstmt.setString(3, modoEntrega);
                pstmt.setString(4, disponibilidad);
                pstmt.setString(5, estadoSolicitud);

                pstmt.executeUpdate();
                System.out.println("Solicitud registrada para el libro ID: " + libroId);
            }
        } catch (Exception e) {
            System.err.println("Error al crear la solicitud: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Método para obtener la disponibilidad desde el estado del libro en la base de datos
    private String obtenerDisponibilidadDesdeEstado(int libroId) {
        String sql = "SELECT estado FROM libros WHERE id_libro = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, libroId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String estadoLibro = rs.getString("estado");
                    return mapearDisponibilidad(estadoLibro);
                }
            }
        } catch (Exception e) {
            System.err.println("Error al consultar el estado del libro ID: " + libroId);
            e.printStackTrace();
        }
        return null; // Retornar null si no se pudo determinar el estado
    }

    // Método para mapear el estado del libro a la disponibilidad de la solicitud
    private String mapearDisponibilidad(String estadoLibro) {
        if ("DEVUELTO".equalsIgnoreCase(estadoLibro)) {
            return "DISPONIBLE";
        } else if ("EN PRÉSTAMO".equalsIgnoreCase(estadoLibro) || "CON RETRASO".equalsIgnoreCase(estadoLibro)) {
            return "EN PRÉSTAMO";
        }
        return null; // Retornar null si el estado no es válido
    }

    // Método para obtener el ID del usuario por nombre
    private int obtenerUsuarioIdPorNombre(String nombreUsuario) {
        String sql = "SELECT id_usuario FROM usuarios WHERE nombre = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, nombreUsuario);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id_usuario");
                }
            }
        } catch (Exception e) {
            System.err.println("Error al obtener el ID del usuario: " + e.getMessage());
            e.printStackTrace();
        }
        return -1; // Retorna -1 si no se encuentra el usuario
    }

    // Modificar el método para "Ir a Recoger"
    @FXML
    private void onIrARecoger(ActionEvent event) {
        for (CestaLibro cestaLibro : librosEnCesta) {
            Libros libro = cestaLibro.getLibro();
            crearSolicitud(libro.getId_libro(), "RECOGIDA");
        }
        System.out.println("Solicitudes de recogida creadas.");
    }

    // Modificar el método para "A Domicilio"
    @FXML
    private void onADomicilio(ActionEvent event) {
        for (CestaLibro cestaLibro : librosEnCesta) {
            Libros libro = cestaLibro.getLibro();
            crearSolicitud(libro.getId_libro(), "A DOMICILIO");
        }
        System.out.println("Solicitudes de envío a domicilio creadas.");
    }




    
    @FXML
    private void cerrarVentana() {
        // Lógica para cerrar la ventana
        Stage stage = (Stage) contenedorLibros.getScene().getWindow();
        stage.close();
    }
}