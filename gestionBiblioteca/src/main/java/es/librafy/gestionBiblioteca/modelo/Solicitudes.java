package es.librafy.gestionBiblioteca.modelo;

public class Solicitudes {
    private int id_solicitud;
    private int usuario_id;
    private int libro_id;
    private String modo_entrega;
    private String disponibilidad;
    private String estado;

    public Solicitudes(int id_solicitud, int usuario_id, int libro_id, String modo_entrega, String disponibilidad, String estado) {
        super();
    	this.id_solicitud = id_solicitud;
        this.usuario_id = usuario_id;
        this.libro_id = libro_id;
        this.modo_entrega = modo_entrega;
        this.disponibilidad = disponibilidad;
        this.estado = estado;
    }

    public int getId_solicitud() {
        return id_solicitud;
    }

    public void setId_solicitud(int id_solicitud) {
        this.id_solicitud = id_solicitud;
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

    public String getModo_entrega() {
        return modo_entrega;
    }

    public void setModo_entrega(String modo_entrega) {
        this.modo_entrega = modo_entrega;
    }

    public String getDisponibilidad() {
        return disponibilidad;
    }

    public void setDisponibilidad(String disponibilidad) {
        this.disponibilidad = disponibilidad;
    }

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
}