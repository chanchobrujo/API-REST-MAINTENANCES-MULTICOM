package com.proyectoIntegrador.MultiCom.service.user;

import com.proyectoIntegrador.MultiCom.entity.*;
import com.proyectoIntegrador.MultiCom.model.dto.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserService UsuarioService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = UsuarioService.getByEmail(email).get();
        return UserPrincipal.build(user);
    }
}