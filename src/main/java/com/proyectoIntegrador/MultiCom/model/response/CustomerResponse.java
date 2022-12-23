package com.proyectoIntegrador.MultiCom.model.response;

public class CustomerResponse {
    private String nombre;
    private String apellido;
    private String numero;
    private String email;

    public
    CustomerResponse (){
        
    }

    public
    CustomerResponse (String nombre, String apellido, String numero, String email){
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