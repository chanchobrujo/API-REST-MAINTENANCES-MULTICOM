package com.proyectoIntegrador.MultiCom.service.user;

import lombok.RequiredArgsConstructor;

import com.proyectoIntegrador.MultiCom.repository.UserRepository;
import com.proyectoIntegrador.MultiCom.exception.BusinessException;

import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static com.proyectoIntegrador.MultiCom.model.dto.UserPrincipal.build;

@Service
@RequiredArgsConstructor
public class UserDetailPrincipalService implements UserDetailsService {

    private final
    UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return build(this.repository.findByEmail(email).orElseThrow(() -> new BusinessException("Usuario no encotnrado.")));
    }
}