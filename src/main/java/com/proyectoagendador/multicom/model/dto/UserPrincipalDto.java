package com.proyectoagendador.multicom.model.dto;

import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@NoArgsConstructor
public class UserPrincipalDto implements UserDetails {
    private String email;
    private String password;
    
    private Collection<? extends GrantedAuthority> authorities;

    public
    UserPrincipalDto (String email, String password, Collection<? extends GrantedAuthority> authorities ){
        this.authorities = authorities;
        this.password = password;
        this.email = email;
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
        return password;
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
}