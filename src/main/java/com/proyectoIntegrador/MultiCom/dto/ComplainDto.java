package com.proyectoIntegrador.MultiCom.dto;

import java.util.*;  

public class ComplainDto {
    private int idreserva;
    private String descripcion;
    private String motivo; 

	public ComplainDto(int idreserva, String descripcion, String motivo) { 
		this.idreserva = idreserva;
		this.descripcion = descripcion;
		this.motivo = motivo;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getMotivo() {
		return motivo;
	}
	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}
	public int getIdreserva() {
		return idreserva;
	}
	public void setIdreserva(int idreserva) {
		this.idreserva = idreserva;
	} 
	
}
