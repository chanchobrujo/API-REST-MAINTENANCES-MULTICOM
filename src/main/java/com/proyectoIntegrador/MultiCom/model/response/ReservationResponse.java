package com.proyectoIntegrador.MultiCom.model.response;

import java.util.*; 

public class ReservationResponse {
    private String fecha;
    private String horaInicio;
    private String horaFin;
    private String estado;
    private String propocito;
    private Set<String> clientes = new HashSet<>();  
    private int idusuario;

    public
    ReservationResponse (){
        
    }

    public
    ReservationResponse (String propocito, String fecha, String horaFin, String horaInicio, String estado, Set<String> clientes, int idusuario){
        this.propocito = propocito;
        this.fecha = fecha;
        this.horaFin = horaFin;
        this.horaInicio = horaInicio;
        this.estado = estado;
        this.clientes= clientes;
        this.idusuario= idusuario;
    }  
    
    public String getPropocito() {
		return propocito;
	}

	public void setPropocito(String propocito) {
		this.propocito = propocito;
	}

	public int getIdusuario() {
        return idusuario;
    }

    public String getFecha() {
        return fecha;
    }
    
    public String getHoraFin() {
        return horaFin;
    }
    
    public String getHoraInicio() {
        return horaInicio;
    }

    public String getEstado() {
        return estado;
    }

    public Set<String> getClientes() {
        return clientes;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
    
    public void setIdusuario(int idusuario) {
        this.idusuario = idusuario;
    }

    public void setHoraFin(String horaFin) {
        this.horaFin = horaFin;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public void setClientes(Set<String> clientes) {
        this.clientes = clientes;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

}