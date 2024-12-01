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
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class InterfazPrincipalControlador {

    private String usuarioActual;  // Declarar la variable para almacenar el nombre del usuario

    @FXML
    private TilePane tilePaneCategorias;
    
    @FXML
    private TextField txt_busqueda;
    
    @FXML
    private Button btn_buscar;
    
    @FXML
    private Button btn_cuenta;
    
    @FXML
    private Button btn_cerrar;
    
    @FXML
    private Label lblUsuario;
    
    @FXML
    private ListView<String> listViewLibros; // Suponiendo que tienes un ListView para mostrar los títulos de los libros
    @FXML
    private Label lblMensaje;
    
    private Scene escenaResultados;

    // Método para establecer el nombre del usuario
    public void setUsuarioActual(String usuario) {
        // Guardamos el usuario actual en la sesión
        SesionUsuario.setUsuarioActual(usuario);
        System.out.println("Usuario actual: " + usuario);  // Para depurar

        // Actualizamos la interfaz con el nombre del usuario
        if (lblUsuario != null) {
            lblUsuario.setText("Bienvenido, " + usuario);
        }
    }

    @FXML
    public void initialize() {
        cargarCategorias();

        // Asociamos los eventos de los botones a sus acciones
        btn_buscar.setOnAction(e -> buscarLibros());
        txt_busqueda.setOnAction(e -> buscarLibros());
        btn_cuenta.setOnAction(e -> abrirCuenta());
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



    private void cargarCategorias() {
        // Lista de categorías y la misma imagen para cada una
        String[] categorias = {"Ficción", "No Ficción", "Infantil", "Inglés", "Misterio y suspense"};
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

    @FXML
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

            // Guardar resultados en sesión
            SesionUsuario.setResultadosBusqueda(resultados);

            // Navegar a Resultados de Búsqueda
            if (escenaResultados == null) {
                // Cargar escena solo la primera vez
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/es/librafy/gestionBiblioteca/vista/ResultadosBusqueda.fxml"));
                Parent root = loader.load();
                escenaResultados = new Scene(root);

                // Pasar resultados al controlador de la escena de resultados
                ResultadosBusquedaControlador controlador = loader.getController();
                controlador.mostrarResultados(resultados);
            } else {
                // Actualizar datos en la escena reutilizada
                ResultadosBusquedaControlador controlador = (ResultadosBusquedaControlador) escenaResultados.getUserData();
                if (controlador != null) {
                    controlador.mostrarResultados(resultados);
                }
            }

            // Cambiar a la escena de resultados
            Stage stage = (Stage) btn_buscar.getScene().getWindow();
            stage.setScene(escenaResultados);

        } catch (Exception e) {
            e.printStackTrace();
            mostrarMensaje("Error al buscar libros.");
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
    
    
    public void actualizarDatos() {
        // Obtener los resultados de la búsqueda desde la sesión
        List<Libros> libros = SesionUsuario.getResultadosBusqueda();

        // Limpiar la vista actual (por ejemplo, el ListView o cualquier componente que contenga datos antiguos)
        listViewLibros.getItems().clear();

        // Si hay resultados, mostramos los títulos de los libros (o cualquier otro dato que desees mostrar)
        if (libros != null && !libros.isEmpty()) {
            for (Libros libro : libros) {
                // Aquí puedes agregar los datos que quieras mostrar, por ejemplo, los títulos de los libros
                listViewLibros.getItems().add(libro.getTitulo());
            }
            lblMensaje.setText(""); // Limpiar el mensaje si hay resultados
        } else {
            // Si no hay resultados, mostramos un mensaje de advertencia
            lblMensaje.setText("No se encontraron resultados.");
        }
    }
    
    @FXML
    private void abrirCesta(ActionEvent event) throws IOException {
        // Cargar la ventana de la cesta (FXML)
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/es/librafy/gestionBiblioteca/vista/Cesta.fxml"));
        Parent root = loader.load();

        // Obtener el controlador de la ventana de la cesta
        CestaControlador cestaControlador = loader.getController();

        // Pasar los libros en la cesta al controlador de la ventana de la cesta
        cestaControlador.setLibrosEnCesta(SesionUsuario.getLibrosEnCesta()); // Acceder a los métodos estáticos de la clase SesionUsuario

        // Crear un nuevo Stage (nueva ventana) para la cesta
        Stage cestaStage = new Stage();
        cestaStage.initStyle(StageStyle.UNDECORATED);
        cestaStage.setTitle("Cesta de Compras"); // Título de la nueva ventana
        cestaStage.setScene(new Scene(root));  // Establecer la escena para la ventana de la cesta
        cestaStage.show(); // Mostrar la nueva ventana

        // No cerrar la ventana actual, solo abrir la ventana de la cesta en una nueva ventana
    }
    
    
}

