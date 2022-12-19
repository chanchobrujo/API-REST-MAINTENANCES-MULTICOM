package com.proyectoIntegrador.MultiCom.model.dto;

import com.proyectoIntegrador.MultiCom.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import static java.util.Collections.singleton;

public class UserPrincipal implements UserDetails {
	private String id;
	private String nombre;
    private String apellido;
    private String numero;
    private String email;
    private String contrasena; 
    private Boolean estado;
    
    private Collection<? extends GrantedAuthority> authorities;

    public
    UserPrincipal (String id, String nombre, String apellido, String numero, String email, String contrasena, Boolean estado, Collection<? extends GrantedAuthority> authorities ){
        this.nombre = nombre;
        this.id = id;
        this.apellido = apellido;
        this.numero = numero;
        this.estado = estado;
        this.authorities = authorities;
        this.email = email;
        this.contrasena = contrasena;
    } 

    public static
    UserPrincipal build(User user){
        return new UserPrincipal(user.getId(), user.getFirstName(), user.getLastName(), user.getNumberPhone(), user.getEmail(), user.getPassword(),
                user.getState(), singleton(new SimpleGrantedAuthority(user.getIdRole().getName())));
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
    
    public String getId() {
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