package es.librafy.gestionBiblioteca.controlador;

import java.util.List;

import es.librafy.gestionBiblioteca.modelo.Libros;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;

public class ResultadosBusquedaControlador {
	
	@FXML
    private TilePane tilePaneResultados;

    // Este método se llamará desde el controlador anterior al cargar los resultados
    public void mostrarResultados(List<Libros> libros) {
        tilePaneResultados.getChildren().clear(); // Limpiar resultados anteriores

        for (Libros libro : libros) {
            tilePaneResultados.getChildren().add(crearTarjetaLibro(libro));
        }
    }

    // Método para crear una tarjeta que representa un libro
    private StackPane crearTarjetaLibro(Libros libro) {
        StackPane tarjeta = new StackPane();

        // Imagen del libro (puedes cambiar la ruta de la imagen según tu necesidad)
        ImageView imageView = new ImageView(new Image(getClass().getResourceAsStream("/imagenes/cesta.png")));
        imageView.setFitWidth(100);
        imageView.setFitHeight(150);

        // Etiqueta que muestra el título y el autor del libro
        Label label = new Label(libro.getTitulo() + "\n" + libro.getAutor());
        label.setStyle("-fx-font-size: 14px; -fx-text-fill: black; -fx-background-color: rgba(255, 255, 255, 0.8);");
        StackPane.setAlignment(label, javafx.geometry.Pos.BOTTOM_CENTER);

        tarjeta.getChildren().addAll(imageView, label);
        return tarjeta;
    }

}
