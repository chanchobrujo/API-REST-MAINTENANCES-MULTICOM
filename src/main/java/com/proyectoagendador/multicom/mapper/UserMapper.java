package com.proyectoagendador.multicom.mapper;

import com.proyectoagendador.multicom.entity.User;
import com.proyectoagendador.multicom.model.dto.UserPrincipalDto;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import static java.util.Collections.singleton;

public
class UserMapper {

    public static
    UserDetails buildDetailAuth(User user) {
        return new UserPrincipalDto(user.getEmail(), user.getPassword(), singleton(new SimpleGrantedAuthority(user.getRole().getName())));
    }
}
