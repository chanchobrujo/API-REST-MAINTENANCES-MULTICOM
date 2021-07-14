package com.proyectoIntegrador.MultiCom.dto;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.GrantedAuthority;
import java.util.Collection;

public class JwtDto{
    private String token;
    private String bearer = "Bearer"; 
    private UserDetails userDetails;
    private Collection<? extends GrantedAuthority> authorities;

    public JwtDto(String token, UserDetails userDetails, Collection<? extends GrantedAuthority> authorities){
        this.token = token;
        this.userDetails = userDetails;
        this.authorities = authorities;
    }
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }
    public String getBearer() {
        return bearer;
    } 
    public UserDetails getUserDetails() {
        return userDetails;
    }
    public String getToken() {
        return token;
    }
    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
    }
    public void setBearer(String bearer) {
        this.bearer = bearer;
    } 
    public void setUserDetails(UserDetails userDetails) {
        this.userDetails = userDetails;
    }
    public void setToken(String token) {
        this.token = token;
    }
}