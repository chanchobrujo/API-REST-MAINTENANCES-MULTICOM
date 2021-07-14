package com.proyectoIntegrador.MultiCom.dto;

public class ClientDto { 
    private String nombre;
    private String apellido;
    private String numero;
    private String email;

    public ClientDto(){
        
    }

    public ClientDto(String nombre, String apellido, String numero, String email){
        this.nombre = nombre; 
        this.apellido = apellido; 
        this.numero = numero; 
        this.email = email;
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

}