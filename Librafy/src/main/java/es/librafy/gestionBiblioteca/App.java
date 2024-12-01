package es.librafy.gestionBiblioteca;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class App extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        // Mostrar el Splash Screen
        showSplashScreen(stage);
    }

    private void showSplashScreen(Stage stage) {
        // Cargar la imagen
        Image image = new Image("/imagenes/portada_carga.png"); // Cambia la ruta si es necesario
        ImageView logo = new ImageView(image);

        // Escalar la imagen (ajusta los valores según lo que necesites)
        double scaledWidth = 300; // Ancho deseado
        double scaledHeight = image.getHeight() * (scaledWidth / image.getWidth()); // Mantener proporción
        logo.setFitWidth(scaledWidth);
        logo.setFitHeight(scaledHeight);

        // Crear un contenedor para incluir la imagen (opcionalmente con texto)
        VBox splashLayout = new VBox(10);
        splashLayout.setStyle("-fx-alignment: center; -fx-background-color: #FFFFFF;");
        splashLayout.getChildren().add(logo);

        // Crear la escena con el tamaño exacto de la imagen escalada
        Scene splashScene = new Scene(splashLayout, scaledWidth, scaledHeight);

        
        // Configurar el Stage
        stage.setScene(splashScene);
        stage.setTitle("Cargando...");
        stage.show();

        // Duración del Splash Screen
        PauseTransition pause = new PauseTransition(Duration.seconds(3)); // Ajusta el tiempo si es necesario
        pause.setOnFinished(event -> showMainScene(stage));
        pause.play();
    }


    private void showMainScene(Stage stage) {
        try {
            // Cargar la escena principal desde inicio.fxml
            Parent root = FXMLLoader.load(getClass().getResource("/es/librafy/gestionBiblioteca/vista/inicio.fxml"));
            Scene mainScene = new Scene(root);
            stage.setTitle("Librafy");
            stage.setScene(mainScene);

            // Centrar la ventana en la pantalla
            stage.centerOnScreen();

            // Mostrar la ventana principal
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
