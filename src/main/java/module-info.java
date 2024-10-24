module es.librafy.gestionbiblio {
    requires javafx.controls;
    requires javafx.fxml;

    opens es.librafy.gestionbiblio to javafx.fxml;
    exports es.librafy.gestionbiblio;
}
