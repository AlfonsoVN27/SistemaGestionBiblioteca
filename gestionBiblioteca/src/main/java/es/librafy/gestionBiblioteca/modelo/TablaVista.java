package es.librafy.gestionBiblioteca.modelo;

public class TablaVista {
	private int id_usuario;
	private String nombre;
    private String fecha_nacimiento;
    private String email;
    private String num_telefono;
    private String dni;
    private String contrasena;
	private int id_libro;
	private String titulo;
	private String autor;
	private String fecha_prestamo;
	private String fecha_devolucion;
	private String categoria;
	private String estado;
	private int id_solicitud;
    private int usuario_id;
    private int libro_id;
    private String modo_entrega;
    private String disponibilidad;
    private int id_historial;
    private String id;
    private String fechaPrestamo;
    private String fechaDevolucion;
    
	public TablaVista(int id_usuario, String nombre, String fecha_nacimiento, String email, String num_telefono,
			String dni, String contrasena, int id_libro, String titulo, String autor, String fecha_prestamo,
			String fecha_devolucion, String categoria, String estado, int id_solicitud, int usuario_id, int libro_id,
			String modo_entrega, String disponibilidad, int id_historial,String id, String fechaPrestamo, String fechaDevolucion) {
		super();
		this.id_usuario = id_usuario;
		this.nombre = nombre;
		this.fecha_nacimiento = fecha_nacimiento;
		this.email = email;
		this.num_telefono = num_telefono;
		this.dni = dni;
		this.contrasena = contrasena;
		this.id_libro = id_libro;
		this.titulo = titulo;
		this.autor = autor;
		this.fecha_prestamo = fecha_prestamo;
		this.fecha_devolucion = fecha_devolucion;
		this.categoria = categoria;
		this.estado = estado;
		this.id_solicitud = id_solicitud;
		this.usuario_id = usuario_id;
		this.libro_id = libro_id;
		this.modo_entrega = modo_entrega;
		this.disponibilidad = disponibilidad;
		this.id_historial = id_historial;
	}

	public int getId_usuario() {
		return id_usuario;
	}

	public void setId_usuario(int id_usuario) {
		this.id_usuario = id_usuario;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getFecha_nacimiento() {
		return fecha_nacimiento;
	}

	public void setFecha_nacimiento(String fecha_nacimiento) {
		this.fecha_nacimiento = fecha_nacimiento;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNum_telefono() {
		return num_telefono;
	}

	public void setNum_telefono(String num_telefono) {
		this.num_telefono = num_telefono;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

	public int getId_libro() {
		return id_libro;
	}

	public void setId_libro(int id_libro) {
		this.id_libro = id_libro;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
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

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
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

	public int getId_historial() {
		return id_historial;
	}

	public void setId_historial(int id_historial) {
		this.id_historial = id_historial;
	}
	// Getter y Setter para id
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    // Getter y Setter para autor
    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    // Getter y Setter para fechaPrestamo
    public String getFechaPrestamo() {
        return fechaPrestamo;
    }

    public void setFechaPrestamo(String fechaPrestamo) {
        this.fechaPrestamo = fechaPrestamo;
    }

    // Getter y Setter para fechaDevolucion
    public String getFechaDevolucion() {
        return fechaDevolucion;
    }

    public void setFechaDevolucion(String fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }
}    

