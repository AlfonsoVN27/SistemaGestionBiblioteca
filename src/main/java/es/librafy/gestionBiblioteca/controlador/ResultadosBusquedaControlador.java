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
import javafx.geometry.Orientation;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ResultadosBusquedaControlador {

	@FXML
	private TilePane tilePaneResultados;
	@FXML
	private Button btn_cuenta;
	@FXML
	private Button btn_buscar;
	@FXML
	private Button btn_volver;
	@FXML
	private TextField txt_busqueda;
	@FXML
	private Button btn_novedades;
	@FXML
	private Button btnVerFicha;

	@FXML
    public void initialize() {
		btn_buscar.setOnAction(e -> buscarLibros());
        txt_busqueda.setOnAction(e -> buscarLibros());
        btn_cuenta.setOnAction(e -> abrirCuenta());
        btn_volver.setOnAction(e -> volver());
        
        List<Libros> resultados = SesionUsuario.getResultadosBusqueda();

        if (resultados != null && !resultados.isEmpty()) {
            mostrarResultados(resultados);  // Método que muestras los resultados en la interfaz
        } else {
            mostrarMensaje("No se encontraron resultados previos.");
        }
        
        // Hacer que el TilePane ocupe todo el ancho disponible y permita el scroll vertical.
        tilePaneResultados.setPrefColumns(1);  // Para asegurar que los elementos se muestren en una columna (si es el caso)
        tilePaneResultados.setVgap(10); // Espaciado vertical entre los elementos
        tilePaneResultados.setHgap(10); // Espaciado horizontal entre los elementos
        tilePaneResultados.setMaxWidth(Double.MAX_VALUE); // Hacer que el TilePane ocupe todo el ancho
    }




   

    // Este método se llamará desde el controlador anterior al cargar los resultados
	private HBox crearTarjetaLibro(Libros libro) {
	    // Crear el HBox principal que contendrá la imagen y los botones
	    HBox tarjeta = new HBox(15);  // El espaciado de 10 entre los elementos
	    tarjeta.setPrefHeight(225); // Ajustamos la altura para la imagen más grande
	    tarjeta.setMaxWidth(Double.MAX_VALUE);  // Asegura que el HBox pueda expandirse horizontalmente

	    // Imagen del libro
	    ImageView imageView = new ImageView(new Image(getClass().getResourceAsStream("/imagenes/" + libro.getRutaImagen())));
	    imageView.setFitWidth(200);  // Aumenta aún más el ancho de la imagen
	    imageView.setFitHeight(300);  // Aumenta aún más la altura de la imagen

	    // Etiqueta que muestra el título y el autor del libro
	    Label label = new Label(libro.getTitulo() + "\n" + libro.getAutor());
	    label.setStyle("-fx-font-size: 24px; -fx-text-fill: black; -fx-background-color: rgba(255, 255, 255, 0.8);");
	    label.setMaxWidth(Double.MAX_VALUE);  // El Label se expandirá horizontalmente ocupando el espacio disponible

	    // Crear VBox para los botones, alineados verticalmente
	    VBox vBoxBotones = new VBox(10);  // Espaciado entre los botones
	    vBoxBotones.setStyle("-fx-padding: 10;");
	    vBoxBotones.setAlignment(javafx.geometry.Pos.CENTER); // Los botones estarán alineados al centro del VBox
	    vBoxBotones.setPrefHeight(150); // Aseguramos que todos los botones tengan la misma altura
	    vBoxBotones.setMaxWidth(150); // Limitar el ancho para evitar que los botones se expandan

	    // Botón "Ver ficha"
	    Button btnVerFicha = new Button("Ver ficha");
	    btnVerFicha.setOnAction(e -> verFichaLibro(libro));
	    btnVerFicha.setPrefWidth(150);  // Fijar un tamaño mayor para el botón
	    btnVerFicha.setPrefHeight(30);  // Ajustar la altura para que sea más grande
	    btnVerFicha.setStyle("-fx-background-radius: 15; -fx-background-color: #FFFFFF; -fx-text-fill: black; -fx-border-color: gray; -fx-border-width: 1; -fx-border-radius: 15;");

	    // Botón "Añadir"
	    Button btnAñadir = new Button("Añadir");
	    btnAñadir.setOnAction(e -> añadirLibro(libro));
	    btnAñadir.setPrefWidth(150);  // Fijar el mismo tamaño para ambos botones
	    btnAñadir.setPrefHeight(30);  // Ajustar la altura para que sea más grande
	    btnAñadir.setStyle("-fx-background-radius: 15; -fx-background-color: #FF47BF; -fx-text-fill: white;");

	    // Añadir los botones al VBox
	    vBoxBotones.getChildren().addAll(btnVerFicha, btnAñadir);

	    // Crear un espacio flexible más grande que empuje los botones aún más a la derecha
	    Region spacer = new Region();
	    spacer.setMinWidth(100); // Aumentar el tamaño del espacio flexible para mover los botones más a la derecha
	    HBox.setHgrow(spacer, Priority.ALWAYS); // Este espacio flexible empuja el VBox hacia la derecha

	    // Añadir la imagen, el texto, el espacio y el VBox de los botones al HBox principal
	    tarjeta.getChildren().addAll(imageView, label, spacer, vBoxBotones);

	    // Devolver la tarjeta
	    return tarjeta;
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

	        if (resultados.isEmpty()) {
	            mostrarMensaje("No se han encontrado resultados para: " + criterio);
	        } else {
	            mostrarResultados(resultados);  // Usar este método para mostrar los resultados
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	        mostrarMensaje("Error en la conexión a la base de datos.");
	    }
	}

/*
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
*/


    public void mostrarResultados(List<Libros> libros) {
        // Limpiar el TilePane para eliminar los resultados previos
        tilePaneResultados.getChildren().clear();

        // Verificar si hay resultados
        if (libros.isEmpty()) {
            Label noResultsLabel = new Label("No se han encontrado resultados.");
            tilePaneResultados.getChildren().add(noResultsLabel);
            return;
        }

        // Agregar cada libro como una tarjeta
        for (Libros libro : libros) {
            // Crear una tarjeta para cada libro
            HBox tarjeta = crearTarjetaLibro(libro);
            tilePaneResultados.getChildren().add(tarjeta);
        }
    }








    // Métodos de acción para los botones
    private void verFichaLibro(Libros libro) {
        try {
            // Cargar el archivo FXML de la ficha del libro
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/es/librafy/gestionBiblioteca/vista/FichaLibro.fxml"));
            Parent root = loader.load();

            // Obtener el controlador de la ficha del libro
            FichaLibroControlador fichaLibroControlador = loader.getController();

            // Inicializar la ficha con el libro seleccionado
            fichaLibroControlador.initialize(libro);

            // Crear la nueva ventana para mostrar la ficha
            Stage stage = new Stage();
            stage.setTitle("Ficha del libro: " + libro.getTitulo());
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            mostrarMensaje("No se pudo cargar la ficha del libro.");
        }
    }


    private void añadirLibro(Libros libro) {
        System.out.println("Añadir libro: " + libro.getTitulo());
        // Aquí puedes agregar la lógica para añadir el libro (ej. agregar al carrito, favoritos, etc.)
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/es/librafy/gestionBiblioteca/vista/InterfazPrincipal.fxml"));
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