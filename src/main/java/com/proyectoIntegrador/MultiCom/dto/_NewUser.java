package com.proyectoIntegrador.MultiCom.dto; 

import javax.validation.constraints.*; 
public class _NewUser { 
	@NotNull
    private String nombre; 
	@NotNull
    private String apellido; 
	@NotNull
    @Min(value = 9)
    private String numero; 
	@Email
	@NotNull
    private String email;  
    private String password;  
    private boolean estado; 

    public _NewUser(){
        
    }

    public _NewUser(String nombre, String apellido, String numero, String email, String password, boolean estado){
        this.nombre = nombre;
        this.apellido = apellido;
        this.numero = numero;
        this.email = email;
        this.password = password; 
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
    public String getPassword() {
        return password;
    } 
    public boolean getEstado() {
        return estado;
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
    public void setPassword(String password) {
        this.password = password;
    } 
    public void setEstado(boolean estado) {
        this.estado = estado;
    }
}