package com.proyectoagendador.multicom.service.user;

import com.proyectoagendador.multicom.exception.BusinessException;
import com.proyectoagendador.multicom.model.dto.UserPrincipal;
import com.proyectoagendador.multicom.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Service
@RequiredArgsConstructor
public class UserDetailPrincipalService implements UserDetailsService {

    private final UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return UserPrincipal.build(this.repository.findByEmail(email).orElseThrow(() -> new BusinessException("Usuario no encotnrado.")));
    }
}