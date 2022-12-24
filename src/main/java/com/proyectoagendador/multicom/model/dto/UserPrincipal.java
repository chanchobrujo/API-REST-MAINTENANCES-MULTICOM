package com.proyectoagendador.multicom.model.dto;

import com.proyectoagendador.multicom.entity.User;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import static java.util.Collections.singleton;

@NoArgsConstructor
public class UserPrincipal implements UserDetails {
    private String email;
    private String password;
    
    private Collection<? extends GrantedAuthority> authorities;

    public
    UserPrincipal (String email, String password, Collection<? extends GrantedAuthority> authorities ){
        this.authorities = authorities;
        this.password = password;
        this.email = email;
    }

    public static
    UserPrincipal build(User user){
        return new UserPrincipal(user.getEmail(), user.getPassword(), singleton(new SimpleGrantedAuthority(user.getIdRole().getName())));
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