package com.proyectoIntegrador.MultiCom.service;

import lombok.RequiredArgsConstructor;

import com.proyectoIntegrador.MultiCom.entity.Role;
import com.proyectoIntegrador.MultiCom.entity.User;
import com.proyectoIntegrador.MultiCom.constants.enums.RoleName;
import com.proyectoIntegrador.MultiCom.model.request.AuthRequest;
import com.proyectoIntegrador.MultiCom.repository.RoleRepository;
import com.proyectoIntegrador.MultiCom.repository.UserRepository;
import com.proyectoIntegrador.MultiCom.security.TokenProviderSecurity;
import com.proyectoIntegrador.MultiCom.exception.BusinessException;
import com.proyectoIntegrador.MultiCom.model.request.SingUpRequest;
import com.proyectoIntegrador.MultiCom.model.response.TokenResponse;
import com.proyectoIntegrador.MultiCom.model.response.MessageResponse;

import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.util.Optional;

import static org.springframework.security.core.context.SecurityContextHolder.getContext;

@Service
@RequiredArgsConstructor
public
class AuthService {

    private final MailSenderService mailSenderService;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProviderSecurity tokenProviderSecurity;
    private final AuthenticationManager authenticationManager;

    public MessageResponse registerForCustomer(SingUpRequest request) {
        MessageResponse response = new MessageResponse("Usuario registrado correctamente.");
        Optional<User> verifyContact = this.userRepository.findByEmailOrNumberPhone(request.getEmail(), request.getNumber());
        Optional<User> verifyDocument = this.userRepository.findByDocumentNumberAndDocumentType(request.getDocumentNumber(), request.getDocument());

        if (verifyContact.isPresent() || verifyDocument.isPresent()) {
            response.setMensaje("Datos repetidos");
        } else {
            Role role = this.roleRepository.findByName(RoleName.ROLE_CUSTOMER.name()).orElseThrow(() -> new BusinessException("Error"));
            User user = new User(request.getName(), request.getSurname(), request.getNumber(), request.getDocument(), request.getDocumentNumber(), request.getEmail(), role, this.passwordEncoder.encode(request.getPassword()));
            this.userRepository.save(user);
            this.mailSenderService.sendMail(request.getEmail(), "Bienvenido a MULTICOM", "Hola es un gusto que formes parte de nuestra empresa.");
        }
        return response;
    }

    public TokenResponse login(AuthRequest authRequest) {
        String email = this.userRepository.findByEmail(authRequest.getEmail()).map(User::getEmail).orElseThrow(() -> new BusinessException("User not found"));
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword());
        Authentication authentication = this.authenticationManager.authenticate(auth);
        getContext().setAuthentication(authentication);
        String token = this.tokenProviderSecurity.generateToken(email);
        return new TokenResponse(token);
    }
}
