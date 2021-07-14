package com.proyectoIntegrador.MultiCom.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class UsuarioPrincipal implements UserDetails {
	private int id;
	private String nombre;
    private String apellido;
    private String numero;
    private String email;
    private String contrasena; 
    private Boolean estado;
    
    private Collection<? extends GrantedAuthority> authorities;

    public UsuarioPrincipal(int id, String nombre, String apellido, String numero, String email, String contrasena, Boolean estado, Collection<? extends GrantedAuthority> authorities ){
        this.nombre = nombre;
        this.id = id;
        this.apellido = apellido;
        this.numero = numero;
        this.estado = estado;
        this.authorities = authorities;
        this.email = email;
        this.contrasena = contrasena;
    } 

    public static UsuarioPrincipal build(Usuario usuario){
        List<GrantedAuthority> authorities =
                usuario.getRoles().stream().map(rol -> new SimpleGrantedAuthority(
                    rol.getRolNombre().name())).collect(Collectors.toList());
        return new UsuarioPrincipal(usuario.getId(), usuario.getNombre(), usuario.getApellido(), usuario.getNumero(), usuario.getEmail(), usuario.getContrasena(), usuario.getEstado(), authorities);
    }
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return contrasena;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
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
}