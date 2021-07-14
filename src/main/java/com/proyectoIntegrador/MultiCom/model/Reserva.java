package com.proyectoIntegrador.MultiCom.model;

import javax.persistence.*; 
import java.util.*; 

@Entity
public class Reserva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; 

	@Column(name="fecha", length = 10, nullable = false) 
    private String fecha;

	@Column(name="horaInicio", length = 5, nullable = false) 
    private String horaInicio;

	@Column(name="horaFin", length = 5, nullable = false) 
    private String horaFin;

	@Column(name="proposito", length = 255, nullable = false) 
    private String proposito;

	@Column(name="estado", length = 50, nullable = false) 
    private String estado;  

	@ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "cliente_reserva", joinColumns = @JoinColumn(name = "reserva_id"),
    inverseJoinColumns = @JoinColumn(name = "cliente_id"))  
    private Set<Cliente> clientes = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "reclamo_reserva", joinColumns = @JoinColumn(name = "reserva_id"),
    inverseJoinColumns = @JoinColumn(name = "reclamo_id"))  
    private Set<Reclamo> reclamo = new HashSet<>(); 

    @ManyToOne
    @JoinColumn(name = "usuario") 
    private Usuario usuario; 
    
    public Reserva() { 
    	
	} 
    
    public Reserva(String fecha, String horaInicio, String horaFin, String estado, Usuario usuario, String proposito) { 
        this.fecha = fecha; 
        this.horaInicio = horaInicio; 
        this.horaFin = horaFin; 
        this.estado = estado;
        this.usuario = usuario;
        this.proposito = proposito; 
	}    

	public Set<Reclamo> getReclamo() {
		return reclamo;
	}

	public void setReclamo(Set<Reclamo> reclamo) {
		this.reclamo = reclamo;
	}

	public String getProposito() {
		return proposito;
	}

	public void setProposito(String proposito) {
		this.proposito = proposito;
	}

	public int getId() {
        return id;
    }

    public Set<Cliente> getClientes() {
        return clientes;
    }

    public String getFecha() {
        return fecha;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public String getHoraFin() {
        return horaFin;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public String getEstado() {
        return estado;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public void setHoraFin(String horaFin) {
        this.horaFin = horaFin;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    } 

    public void setClientes(Set<Cliente> clientes) {
        this.clientes = clientes;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}