package com.proyectoIntegrador.MultiCom.model;

import javax.persistence.*; 
import java.util.*; 

@Entity
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
	@Column(name="nombre", length = 50, unique = true ) 
    private String nombre;

	@Column(name="apellido", length = 50, unique = true )
    private String apellido;

    @Column(name="numero", length = 50, unique = true )
    private String numero;

    @Column(name="email", length = 50, unique = true )
    private String email;

    @Column(columnDefinition = "LONGTEXT")
    private String contrasena;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "usuario_rol", joinColumns = @JoinColumn(name = "usuario_id"),
    inverseJoinColumns = @JoinColumn(name = "rol_id"))
    private Set<Rol> roles = new HashSet<>();

    @Column(name="estado")
	private Boolean estado; 

    public Usuario() { 

	} 

    public Usuario(String nombre, String apellido, String numero, String email, String contrasena, Boolean estado) { 
        this.nombre = nombre; 
        this.apellido = apellido; 
        this.numero = numero; 
        this.email = email;
        this.contrasena = contrasena;
        this.estado = estado;
	} 

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    } 

    public String getApellido() {
        return apellido;
    }

    public String getNumero() {
        return numero;
    }

    public Boolean getEstado() {
        return estado;
    }

    public String getEmail() {
        return email;
    }

    public String getContrasena() {
        return contrasena;
    }

    public Set<Rol> getRoles() {
        return roles;
    } 
    
    public void setId(int id) {
		this.id = id;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}

	public void setRoles(Set<Rol> roles) {
        this.roles = roles;
    }

}