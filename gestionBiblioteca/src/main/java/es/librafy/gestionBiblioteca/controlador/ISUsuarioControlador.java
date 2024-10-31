package es.librafy.gestionBiblioteca.controlador;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class ISUsuarioControlador {
	
	@FXML
    private Button btn_volver;

    //Referencia a la ventana anterior
    private Stage previousStage;

    //Método para establecer la ventana anterior
    public void setPreviousStage(Stage stage) {
        this.previousStage = stage;
    }
	
	@FXML
    public void mostrarMensaje(ActionEvent event) {
        System.out.println("Botón presionado");
    }
	
	@FXML
    public void initialize() {
        btn_volver.setOnAction(event -> volver()); //Configura la acción del botón
    }
	
	public void volver() {
		if (previousStage != null) {
            previousStage.show();
        }
        Stage currentStage = (Stage) btn_volver.getScene().getWindow();
        currentStage.close(); 
    }

}
