package es.librafy.gestionBiblioteca.controlador;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import es.librafy.gestionBiblioteca.modelo.Libros;
import es.librafy.gestionBiblioteca.util.DatabaseConnection;

public class GestionLibrosControlador {

    @FXML
    private TableView<Libros> tablaLibros;
    @FXML
    private TableColumn<Libros, Integer> idColumn;
    @FXML
    private TableColumn<Libros, String> tituloColumn;
    @FXML
    private TableColumn<Libros, String> autorColumn;
    @FXML
    private TableColumn<Libros, String> fechapresColumn;
    @FXML
    private TableColumn<Libros, String> fechadelColumn;
    @FXML
    private TableColumn<Libros, String> categoriaColumn;
    @FXML
    private TableColumn<Libros, String> estadoColumn;

    @FXML
    private Button btn_guardarLibros;
    @FXML
    private Button btn_volver;
    @FXML
    private Button btn_añadirLibro;
    @FXML
    private Button btn_modificarLibro;
    
    @FXML
    private ComboBox<String> comboBoxCategoria;
    @FXML
    private ComboBox<String> comboBoxEstado;
    
    @FXML
    private TextField txt_busqueda;

   
    

    @FXML
    private ComboBox<String> cb_categoriaLibro;  // ComboBox para categoría
    @FXML
    private ComboBox<String> cb_estadoLibro;      // ComboBox para estado

    private ObservableList<Libros> listaLibros;

    // Método para inicializar la tabla y cargar los datos
    public void initialize() {
    	
    	listaLibros = FXCollections.observableArrayList(); // Cargar los libros desde tu fuente de datos
        tablaLibros.setItems(listaLibros);
        
        // Inicializar valores de ComboBox externos
        cb_categoriaLibro.setItems(FXCollections.observableArrayList(
                "Ficción", "No Ficción", "Infantil", "Inglés", "Misterio y suspense"));
        cb_estadoLibro.setItems(FXCollections.observableArrayList(
                "En Préstamo", "Devuelto", "Con Retraso"));

        // Hacer la tabla editable
        tablaLibros.setEditable(true);

        // Configuración de las columnas de la tabla
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id_libro"));
        tituloColumn.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        autorColumn.setCellValueFactory(new PropertyValueFactory<>("autor"));
        fechapresColumn.setCellValueFactory(new PropertyValueFactory<>("fecha_prestamo"));
        fechadelColumn.setCellValueFactory(new PropertyValueFactory<>("fecha_devolucion"));
        categoriaColumn.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        estadoColumn.setCellValueFactory(new PropertyValueFactory<>("estado"));

        // Hacer que las columnas de título, autor y fechas sean editables como campos de texto
        tituloColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        autorColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        fechapresColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        fechadelColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        // Configuramos los eventos para la edición de las columnas de texto
        tituloColumn.setOnEditCommit(event -> {
            Libros libro = event.getRowValue();
            libro.setTitulo(event.getNewValue());
        });
        autorColumn.setOnEditCommit(event -> {
            Libros libro = event.getRowValue();
            libro.setAutor(event.getNewValue());
        });
        fechapresColumn.setOnEditCommit(event -> {
            String nuevaFecha = event.getNewValue();
            // Validar si la fecha es correcta antes de actualizarla
            if (esFechaValida(nuevaFecha)) {
                Libros libro = event.getRowValue();
                libro.setFecha_prestamo(nuevaFecha);  // Guardamos como String
            } else {
                mostrarAlerta("Fecha no válida", "La fecha de préstamo no es válida.");
            }
        });
        fechadelColumn.setOnEditCommit(event -> {
            String nuevaFecha = event.getNewValue();
            // Validar si la fecha es correcta antes de actualizarla
            if (esFechaValida(nuevaFecha)) {
                Libros libro = event.getRowValue();
                libro.setFecha_devolucion(nuevaFecha);  // Guardamos como String
            } else {
                mostrarAlerta("Fecha no válida", "La fecha de devolución no es válida.");
            }
        });

        // Cargar los datos en la tabla
        cargarDatos();
    }
    
    @FXML
    public void realizarBusqueda(KeyEvent event) {
        String textoBusqueda = txt_busqueda.getText().toLowerCase();
        
        // Filtrar los libros según el texto de búsqueda
        ObservableList<Libros> librosFiltrados = FXCollections.observableArrayList();
        
        for (Libros libro : listaLibros) {
            if (libro.getTitulo().toLowerCase().contains(textoBusqueda) ||
                libro.getAutor().toLowerCase().contains(textoBusqueda) ||
                String.valueOf(libro.getId_libro()).contains(textoBusqueda)) {
                librosFiltrados.add(libro);
            }
        }

        // Actualizar la tabla con los libros filtrados
        tablaLibros.setItems(librosFiltrados);
    }

    public static Libros obtenerLibroPorTitulo(String tituloLibro) {
        String query = "SELECT * FROM libros WHERE titulo = ?";  // Cambio aquí de 'nombre' a 'titulo'
        
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/librafybbdd", "root", "");
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, tituloLibro);  // Asignar el título del libro a la consulta
            
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                // Crear un objeto Libros con los datos obtenidos
                return new Libros(
                    rs.getInt("id_libro"),
                    rs.getString("titulo"),  // Cambio aquí de 'nombre' a 'titulo'
                    rs.getString("autor"),
                    rs.getString("fecha_prestamo"),
                    rs.getString("fecha_devolucion"),
                    rs.getString("categoria"),
                    rs.getString("estado"),
                    rs.getString("rutaImagen"),
                    rs.getString("resumen")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;  // Si no se encuentra el libro
    }
    
    // Método para cargar los datos de los libros desde la base de datos
    private void cargarDatos() {
        listaLibros = FXCollections.observableArrayList();

        String query = "SELECT * FROM libros"; // Consulta para obtener los libros

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            // Recorrer los resultados y agregar los libros a la lista
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

                // Crear objeto Libros y añadirlo a la lista
                listaLibros.add(new Libros(id_libro, titulo, autor, fecha_prestamo, fecha_devolucion, categoria, estado, rutaImagen, resumen));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Establecer los libros en la TableView
        tablaLibros.setItems(listaLibros);
    }
    
    

    // Método para mostrar alerta de error
    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    // Método para validar que una fecha esté en formato correcto (yyyy-MM-dd)
    private boolean esFechaValida(String fecha) {
        try {
            SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
            formato.setLenient(false); // Esto asegura que las fechas no sean ambiguas, por ejemplo 30 de febrero
            formato.parse(fecha);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    // Método para aplicar los valores seleccionados del ComboBox a la fila seleccionada
    @FXML
    private void aplicarFiltros() {
        // Obtener el libro seleccionado en la tabla
        Libros libroSeleccionado = tablaLibros.getSelectionModel().getSelectedItem();

        if (libroSeleccionado != null) {
            // Obtener los valores seleccionados de los ComboBox
            String categoriaSeleccionada = cb_categoriaLibro.getValue();
            String estadoSeleccionado = cb_estadoLibro.getValue();

            // Asignar los valores seleccionados al libro
            if (categoriaSeleccionada != null) {
                libroSeleccionado.setCategoria(categoriaSeleccionada);
            }
            if (estadoSeleccionado != null) {
                libroSeleccionado.setEstado(estadoSeleccionado);
            }

            // Actualizar los datos en la base de datos
            actualizarLibros();

            // Refrescar la tabla para mostrar los cambios
            cargarDatos();
            tablaLibros.refresh();
        } else {
            System.out.println("Por favor, selecciona un libro de la tabla.");
        }
    }

    // Método para actualizar los libros en la base de datos
    private void actualizarLibros() {
        String query = "UPDATE libros SET titulo = ?, autor = ?, fecha_prestamo = ?, fecha_devolucion = ?, categoria = ?, estado = ? WHERE id_libro = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            for (Libros libro : tablaLibros.getItems()) {
                pstmt.setString(1, libro.getTitulo());
                pstmt.setString(2, libro.getAutor());
                pstmt.setString(3, libro.getFecha_prestamo());
                pstmt.setString(4, libro.getFecha_devolucion());
                pstmt.setString(5, libro.getCategoria());
                pstmt.setString(6, libro.getEstado());
                pstmt.setInt(7, libro.getId_libro());
                pstmt.addBatch();
            }

            pstmt.executeBatch();
            System.out.println("Datos actualizados correctamente");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void añadirLibro() {
        // Obtener un nuevo ID único para el libro
        int nuevoIdLibro = obtenerNuevoIdLibro();


        // Crear un nuevo libro
        Libros nuevoLibro = new Libros(nuevoIdLibro, "", "", "", "", "Ficción", "DEVUELTO", "", "");

        // Añadirlo a la lista y actualizar la tabla
        listaLibros.add(nuevoLibro);
        tablaLibros.setItems(listaLibros);
        tablaLibros.refresh();  // Refrescar la tabla para mostrar el nuevo libro
    }


    @FXML
    private void guardarLibros() {
        // Si no hay libros en la lista, no hacemos nada
        if (listaLibros.isEmpty()) {
            return;
        }

        // Tomamos el último libro agregado (el nuevo libro)
        Libros libroNuevo = listaLibros.get(listaLibros.size() - 1);

        // La consulta para insertar el libro en la base de datos
        String query = "INSERT INTO libros (titulo, autor, fecha_prestamo, fecha_devolucion, categoria, estado) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            // Solo guardar el libro nuevo si tiene información completa
            if (!libroNuevo.getTitulo().isEmpty() && !libroNuevo.getAutor().isEmpty()) {
                pstmt.setString(1, libroNuevo.getTitulo());
                pstmt.setString(2, libroNuevo.getAutor());
                pstmt.setString(3, libroNuevo.getFecha_prestamo());
                pstmt.setString(4, libroNuevo.getFecha_devolucion());
                pstmt.setString(5, libroNuevo.getCategoria());  // Categoria seleccionada
                pstmt.setString(6, libroNuevo.getEstado());  // Estado seleccionado
                pstmt.executeUpdate();

                System.out.println("El libro ha sido guardado correctamente en la base de datos.");

                // Opcional: Mostrar mensaje de éxito al usuario
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Éxito");
                alert.setHeaderText(null);
                alert.setContentText("El libro ha sido guardado correctamente.");
                alert.showAndWait();

                // Limpiar la lista de libros después de guardar el libro nuevo (si es necesario)
                listaLibros.clear();
                tablaLibros.setItems(listaLibros);
            } else {
                // Si el libro no tiene información completa, mostrar error
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("El libro no tiene información suficiente para ser guardado.");
                alert.showAndWait();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            // Mostrar mensaje de error si ocurre un problema
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Error al guardar el libro en la base de datos.");
            alert.showAndWait();
        }
    }

    
    @FXML
    private void modificarLibros() {
        actualizarLibros();
    }

    
    @FXML
    private void eliminarLibro() {
        // Obtener el libro seleccionado en la tabla
        Libros libroSeleccionado = tablaLibros.getSelectionModel().getSelectedItem();

        // Verificar si hay un libro seleccionado
        if (libroSeleccionado != null) {
            // Confirmar eliminación con el usuario
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmar eliminación");
            alert.setHeaderText(null);
            alert.setContentText("¿Está seguro de que desea eliminar este libro?");
            
            // Si el usuario confirma la eliminación
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    // Eliminar el libro de la base de datos
                    eliminarLibroDeBaseDeDatos(libroSeleccionado);

                    // Eliminar el libro de la lista en memoria
                    listaLibros.remove(libroSeleccionado);
                    tablaLibros.setItems(listaLibros); // Actualizar la tabla

                    // Opcional: Mostrar un mensaje informando de la eliminación
                    Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);
                    infoAlert.setTitle("Eliminado");
                    infoAlert.setHeaderText(null);
                    infoAlert.setContentText("El libro ha sido eliminado correctamente.");
                    infoAlert.showAndWait();
                }
            });
        } else {
            // Si no hay libro seleccionado, mostrar un mensaje de error
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Debe seleccionar un libro para eliminar.");
            alert.showAndWait();
        }
    }

    // Método para eliminar el libro de la base de datos
    private void eliminarLibroDeBaseDeDatos(Libros libro) {
        String query = "DELETE FROM libros WHERE id_libro = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            // Establecer el ID del libro a eliminar
            pstmt.setInt(1, libro.getId_libro());

            // Ejecutar la consulta para eliminar el libro
            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Libro eliminado correctamente de la base de datos.");
            } else {
                System.out.println("No se encontró el libro en la base de datos.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Mostrar mensaje de error si ocurre un problema
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Error al eliminar el libro de la base de datos.");
            alert.showAndWait();
        }
    }


    

    // Método para volver a la ventana principal
    @FXML
    private void volver() {
        Stage stage = (Stage) btn_volver.getScene().getWindow();
        stage.close();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/es/librafy/gestionBiblioteca/vista/AdminPrincipal.fxml"));
            Parent root = loader.load();
            Stage newStage = new Stage();
            newStage.setScene(new Scene(root));
            newStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error al intentar abrir la ventana anterior.");
        }
    }
    
 // Método para obtener un nuevo id_libro único
    private int obtenerNuevoIdLibro() {
        int nuevoId = 1;  // Valor predeterminado

        // Consulta para obtener el último id_libro en la base de datos
        String query = "SELECT MAX(id_libro) FROM libros";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            if (rs.next()) {
                nuevoId = rs.getInt(1) + 1;  // Incrementa el último id_libro
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return nuevoId;
    }
}
