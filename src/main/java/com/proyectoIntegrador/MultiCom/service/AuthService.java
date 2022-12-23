package com.proyectoIntegrador.MultiCom.service;

import java.util.ArrayList;
import java.util.Collection;

import lombok.RequiredArgsConstructor;

import com.proyectoIntegrador.MultiCom.entity.Role;
import com.proyectoIntegrador.MultiCom.entity.User;
import com.proyectoIntegrador.MultiCom.constants.enums.RoleName;
import com.proyectoIntegrador.MultiCom.model.request.AuthRequest;
import com.proyectoIntegrador.MultiCom.repository.RoleRepository;
import com.proyectoIntegrador.MultiCom.repository.UserRepository;
import com.proyectoIntegrador.MultiCom.security.ProviderSecurity;
import com.proyectoIntegrador.MultiCom.exception.BusinessException;
import com.proyectoIntegrador.MultiCom.model.request.SingUpRequest;
import com.proyectoIntegrador.MultiCom.model.response.TokenResponse;
import com.proyectoIntegrador.MultiCom.model.response.MessageResponse;

import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import static org.springframework.security.core.context.SecurityContextHolder.getContext;

@Service
@RequiredArgsConstructor
public
class AuthService {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ProviderSecurity providerSecurity;
    private final AuthenticationManager authenticationManager;

    public MessageResponse registerForCustomer(SingUpRequest request) {
        this.userRepository.findByEmail(request.getEmail()).ifPresent(customer -> {
            throw new BusinessException("Correo ya registrado");
        });
        MessageResponse response = new MessageResponse("Usuario registrado correctamente.");
        try {
            String password = this.passwordEncoder.encode(request.getPassword());
            Role role = this.roleRepository.findByName(RoleName.ROLE_CUSTOMER).orElseThrow(() -> new BusinessException("Error"));
            User customer = new User(request.getName(), request.getSurname(), request.getNumber(), request.getEmail(), role, password);
            BusinessService.sendMail(customer.getEmail(), "Bienvenido a MULTICOM", "Hola es un gusto que formes parte de nuestra empresa.");
            this.userRepository.save(customer);
        } catch (Exception e) {
            response.setMensaje(e.getMessage());
        }
        return response;
    }

    public TokenResponse login(AuthRequest authRequest) {
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword());
        Authentication authentication = this.authenticationManager.authenticate(auth);
        getContext().setAuthentication(authentication);
        String token = this.providerSecurity.generateToken(authentication);
        return new TokenResponse(token);
    }
}
