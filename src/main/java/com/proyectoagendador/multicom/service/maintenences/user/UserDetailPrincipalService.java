package com.proyectoagendador.multicom.service.maintenences.user;

import com.proyectoagendador.multicom.exception.BusinessException;
import com.proyectoagendador.multicom.mapper.UserMapper;
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
        return UserMapper.buildDetailAuth(this.repository.findByEmail(email).orElseThrow(() -> new BusinessException("Usuario no encotnrado.")));
    }
}