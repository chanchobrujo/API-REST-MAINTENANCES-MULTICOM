package com.proyectoIntegrador.MultiCom.model;

import javax.persistence.*; 

@Entity
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; 
    
	@Column(name="nombre", length = 50, unique = true, nullable = false) 
    private String nombre;

	@Column(name="apellido", length = 50, unique = true, nullable = false)
    private String apellido;

    @Column(name="numero", length = 50, unique = true )
    private String numero;

    @Column(name="email", length = 50, unique = true )
    private String email;

    public Cliente(){
        
    }

    public Cliente(String nombre, String apellido, String numero, String email){
        this.nombre = nombre; 
        this.apellido = apellido; 
        this.numero = numero; 
        this.email = email;
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

    public String getEmail() {
        return email;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public void setNumero(String numero) {
        this.numero = numero;
    }
    
}