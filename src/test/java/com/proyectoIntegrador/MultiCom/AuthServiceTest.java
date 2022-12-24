package com.proyectoIntegrador.MultiCom;

import java.util.Optional;

import com.proyectoIntegrador.MultiCom.constants.enums.RoleName;
import com.proyectoIntegrador.MultiCom.entity.Role;
import com.proyectoIntegrador.MultiCom.entity.User;
import com.proyectoIntegrador.MultiCom.exception.BusinessException;
import com.proyectoIntegrador.MultiCom.model.request.AuthRequest;
import com.proyectoIntegrador.MultiCom.model.request.SingUpRequest;
import com.proyectoIntegrador.MultiCom.model.response.MessageResponse;
import com.proyectoIntegrador.MultiCom.repository.RoleRepository;
import com.proyectoIntegrador.MultiCom.repository.UserRepository;
import com.proyectoIntegrador.MultiCom.security.TokenProviderSecurity;
import com.proyectoIntegrador.MultiCom.service.AuthService;
import com.proyectoIntegrador.MultiCom.service.MailSenderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(MockitoJUnitRunner.class)
class AuthServiceTest {

    private final SingUpRequest request = new SingUpRequest("kevin", "palma", "DNI", "72657666", "951", "kevin@gmail.com", "123");

    private final AuthRequest authRequest = new AuthRequest(request.getEmail(), "123");

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private MailSenderService mailSenderService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private TokenProviderSecurity providerSecurity;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthService service;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void registerForCustomerExisting() {
        User user = new User(request.getName(), request.getSurname(), request.getNumber(), request.getDocument(), request.getDocumentNumber(), request.getEmail(), null, "xfg");
        when(this.userRepository.findByEmailOrNumberPhone(request.getEmail() ,request.getNumber())).thenReturn(Optional.of(user));
        assertEquals(this.service.registerForCustomer(this.request), new MessageResponse("Datos repetidos"));
    }
    @Test
    void registerForDocumentCustomerExisting() {
        User user = new User(request.getName(), request.getSurname(), request.getNumber(), request.getDocument(), request.getDocumentNumber(), request.getEmail(), null, "xfg");
        when(this.userRepository.findByDocumentNumberAndDocumentType(request.getDocumentNumber() ,request.getDocument())).thenReturn(Optional.of(user));
        assertEquals(this.service.registerForCustomer(this.request), new MessageResponse("Datos repetidos"));
    }

    @Test
    void registerForCustomer() {
        Role role = new Role(RoleName.ROLE_CUSTOMER.name(), RoleName.ROLE_CUSTOMER.getValue());
        when(this.userRepository.findByEmailOrNumberPhone(request.getEmail() ,request.getNumber())).thenReturn(Optional.empty());
        when(this.roleRepository.findByName(RoleName.ROLE_CUSTOMER.name())).thenReturn(Optional.of(role));
        assertEquals(this.service.registerForCustomer(this.request), new MessageResponse("Usuario registrado correctamente."));
    }

    @Test
    void registerForCustomerRoleNotFound() {
        when(this.userRepository.findByEmailOrNumberPhone(request.getEmail() ,request.getNumber())).thenReturn(Optional.empty());
        when(this.roleRepository.findByName(RoleName.ROLE_CUSTOMER.name())).thenReturn(Optional.empty());
        Throwable throwable =  assertThrows(Throwable.class, () -> this.service.registerForCustomer(request));
        assertEquals(BusinessException.class, throwable.getClass());
    }

    @Test
    void login() {
        User user = new User();
        user.setEmail(this.request.getEmail());
        when(this.userRepository.findByEmail(request.getEmail())).thenReturn(Optional.of(user));
        assertNotNull(this.service.login(this.authRequest));
    }

    @Test
    void loginUserNotFound() {
        when(this.userRepository.findByEmail(request.getEmail())).thenReturn(Optional.empty());
        Throwable throwable =  assertThrows(Throwable.class, () -> this.service.login(this.authRequest));
        assertEquals(BusinessException.class, throwable.getClass());
    }
}

