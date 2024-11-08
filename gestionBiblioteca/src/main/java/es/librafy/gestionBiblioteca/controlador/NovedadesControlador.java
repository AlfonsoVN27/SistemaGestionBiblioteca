package es.librafy.gestionBiblioteca.controlador;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import javafx.animation.FadeTransition;

public class NovedadesControlador {

    @FXML
    private AnchorPane tarjetaNovedad;

    @FXML
    private ImageView imagenNovedad;

    @FXML
    private Label tituloNovedad;

    @FXML
    private Label descripcionNovedad;

    public void initialize() {
        // Configuración inicial de la tarjeta
        imagenNovedad.setImage(new Image("/imagenes/nuevo_libro.jpg"));  // Imagen de ejemplo
        tituloNovedad.setText("¡Nuevo Libro en la Biblioteca!");
        descripcionNovedad.setText("Un emocionante libro sobre aventuras acaba de llegar.");

        // Sombras en la tarjeta
        tarjetaNovedad.setEffect(new DropShadow(10, Color.GRAY));

        // Animación de aparición
        FadeTransition fadeIn = new FadeTransition(Duration.seconds(1), tarjetaNovedad);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        fadeIn.play();
    }

    @FXML
    private void mostrarDetalle() {
        System.out.println("Mostrar detalles de la novedad");
        // Aquí puedes mostrar una nueva ventana o un diálogo con más detalles.
    }
}