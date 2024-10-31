package es.librafy.gestionBiblioteca.modelo;

public class Historial {
    private int id_historial;
    private int usuario_id;
    private int libro_id;
    private String fecha_prestamo;
    private String fecha_devolucion;

    public Historial(int id_historial, int usuario_id, int libro_id, String fecha_prestamo, String fecha_devolucion) {
        super();
    	this.id_historial = id_historial;
        this.usuario_id = usuario_id;
        this.libro_id = libro_id;
        this.fecha_prestamo = fecha_prestamo;
        this.fecha_devolucion = fecha_devolucion;
    }

    public int getId_historial() {
        return id_historial;
    }

    public void setId_historial(int id_historial) {
        this.id_historial = id_historial;
    }

    public int getUsuario_id() {
        return usuario_id;
    }

    public void setUsuario_id(int usuario_id) {
        this.usuario_id = usuario_id;
    }

    public int getLibro_id() {
        return libro_id;
    }

    public void setLibro_id(int libro_id) {
        this.libro_id = libro_id;
    }

    public String getFecha_prestamo() {
        return fecha_prestamo;
    }

    public void setFecha_prestamo(String fecha_prestamo) {
        this.fecha_prestamo = fecha_prestamo;
    }

    public String getFecha_devolucion() {
        return fecha_devolucion;
    }

    public void setFecha_devolucion(String fecha_devolucion) {
        this.fecha_devolucion = fecha_devolucion;
    }
}
