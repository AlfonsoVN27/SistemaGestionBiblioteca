package es.librafy.gestionBiblioteca.modelo;

public class Usuarios {
    private int id_usuario;
    private String nombre;
    private String fecha_nacimiento;
    private String email;
    private String num_telefono;
    private String dni;

    public Usuarios(int id_usuario, String nombre, String fecha_nacimiento, String email, String num_telefono, String dni) {
    	super();
        this.id_usuario = id_usuario;
        this.nombre = nombre;
        this.fecha_nacimiento = fecha_nacimiento;
        this.email = email;
        this.num_telefono = num_telefono;
        this.dni = dni;
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
}
