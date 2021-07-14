package com.proyectoIntegrador.MultiCom.model;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.*; 

@Entity
public class Reclamo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; 
    
	@Column(name="motivo", length = 50, unique = false, nullable = true) 
    private String motivo;
    
	@Column(name="descripcion", length = 255, unique = false, nullable = true) 
    private String descripcion;  
     //
	@Column(name="fecha", length = 50, unique = false) 
    private String fecha;   
    
    public Reclamo() { 

	} 

	public Reclamo( String motivo, String descripcion){ 
		
		String strDateFormat = "yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat objSDF = new SimpleDateFormat(strDateFormat); 
		
		Date fecha = new Date(); 
		
		this.motivo = motivo;
		this.descripcion = descripcion;
		this.fecha = objSDF.format(fecha); 
	} 

	public String getFecha() {
		return fecha;
	} 

	public void setFecha(String fecha) {
		this.fecha = fecha;
	} 

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	
}
