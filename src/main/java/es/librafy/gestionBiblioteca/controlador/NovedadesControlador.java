package es.librafy.gestionBiblioteca.controlador;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.animation.FadeTransition;
import javafx.util.Duration;

public class NovedadesControlador {

    @FXML
    private AnchorPane tarjetaNovedad;

    @FXML
    private ImageView imagenNovedad;

    @FXML
    private Label tituloNovedad;

    @FXML
    private Label descripcionNovedad;

    @FXML
    private Label tituloLabel;  // Etiqueta del título principal (Últimas Novedades)

    @FXML
    private Button btn_cerrar;  // Botón de cierre

    private Stage stagePrincipal;  // Referencia a la ventana principal para quitar el fondo grisáceo

    // Método que se llamará para establecer la ventana principal
    public void setStagePrincipal(Stage stagePrincipal) {
        this.stagePrincipal = stagePrincipal;
    }

    public void initialize() {
        // Configuración inicial de la tarjeta
        imagenNovedad.setImage(new Image("/imagenes/libro_thegrefg.jpg"));  // Imagen de ejemplo
        tituloNovedad.setText("¡Nuevo Libro Añadido!");
        descripcionNovedad.setText("Explora nuestro último libro disponible en la biblioteca.");

        // Sombras en la tarjeta
        tarjetaNovedad.setEffect(new DropShadow(10, Color.GRAY));

        // Animación de aparición para la tarjeta
        FadeTransition fadeIn = new FadeTransition(Duration.seconds(1), tarjetaNovedad);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        fadeIn.play();

        // Efecto de brillo en el texto "Últimas Novedades"
        Glow glow = new Glow(0.3);  // Nivel inicial de brillo
        tituloLabel.setEffect(glow);

        // Animación de brillo intermitente para el título
        FadeTransition glowTransition = new FadeTransition(Duration.seconds(1.5), tituloLabel);
        glowTransition.setFromValue(0.3);  // Brillo mínimo
        glowTransition.setToValue(0.8);    // Brillo máximo
        glowTransition.setCycleCount(FadeTransition.INDEFINITE);
        glowTransition.setAutoReverse(true);
        glowTransition.play();
    }

    @FXML
    private void mostrarDetalle() {
        System.out.println("Mostrar detalles de la novedad");
        // Aquí puedes mostrar una nueva ventana o un diálogo con más detalles si es necesario.
    }

    // Método para cerrar la ventana de Novedades
    @FXML
    private void cerrarVentana() {
        // Obtener la ventana (Stage) actual y cerrarla
        Stage stage = (Stage) btn_cerrar.getScene().getWindow();
        stage.close();  // Cerrar la ventana de novedades

        // Eliminar el fondo grisáceo en la interfaz principal
        if (stagePrincipal != null) {
            stagePrincipal.getScene().getRoot().setEffect(null);  // Eliminar el efecto de color grisáceo
        }
    }
}
