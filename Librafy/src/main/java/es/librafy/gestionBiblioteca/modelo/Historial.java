package es.librafy.gestionBiblioteca.modelo;

public class Historial {
    private int id_historial;
    private int usuario_id;
    private String titulo;
    private String fecha_prestamo;
    private String fecha_devolucion;
    private String estado;

    public Historial(int id_historial, int usuario_id, String titulo, String fecha_prestamo, String fecha_devolucion, String estado) {
        this.id_historial = id_historial;
        this.usuario_id = usuario_id;
        this.titulo = titulo;
        this.fecha_prestamo = fecha_prestamo;
        this.fecha_devolucion = fecha_devolucion;
        this.estado = estado;
    }

    // Getters y setters
    public int getId_historial() { return id_historial; }
    public void setId_historial(int id_historial) { this.id_historial = id_historial; }

    public int getUsuario_id() { return usuario_id; }
    public void setUsuario_id(int usuario_id) { this.usuario_id = usuario_id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getFecha_prestamo() { return fecha_prestamo; }
    public void setFecha_prestamo(String fecha_prestamo) { this.fecha_prestamo = fecha_prestamo; }

    public String getFecha_devolucion() { return fecha_devolucion; }
    public void setFecha_devolucion(String fecha_devolucion) { this.fecha_devolucion = fecha_devolucion; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}
