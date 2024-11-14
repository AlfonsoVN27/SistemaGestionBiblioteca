package es.librafy.gestionBiblioteca.modelo;

public class Usuarios {
    private int id_usuario;
    private String nombre;
    private String dni;
    private String fecha_nacimiento;
    private String direccion;
    private String num_telefono;
    private String nomUsuario;
    private String email;
    private String contrasena;

    

	

    public Usuarios(int id_usuario, String nombre, String dni, String fecha_nacimiento, String direccion, String num_telefono, String nomUsuario, String email, String contrasena) {
    	super();
        this.id_usuario = id_usuario;
        this.nombre = nombre;
        this.dni = dni;
        this.fecha_nacimiento = fecha_nacimiento;
        this.direccion = direccion;
        this.num_telefono = num_telefono;
        this.nomUsuario = nomUsuario;
        this.email = email;
        this.contrasena = contrasena;
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

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getNomUsuario() {
		return nomUsuario;
	}

	public void setNomUsuario(String nomUsuario) {
		this.nomUsuario = nomUsuario;
	}
	

}
