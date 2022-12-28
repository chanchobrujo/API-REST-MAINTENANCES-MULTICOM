package com.proyectoagendador.multicom.service.authentication;

import java.util.Optional;

import com.proyectoagendador.multicom.common.constants.GeneralConstants;
import com.proyectoagendador.multicom.common.enums.RoleNameEnum;
import com.proyectoagendador.multicom.entity.Role;
import com.proyectoagendador.multicom.entity.User;
import com.proyectoagendador.multicom.exception.BusinessException;
import com.proyectoagendador.multicom.model.request.AuthRequest;
import com.proyectoagendador.multicom.model.request.SingUpRequest;
import com.proyectoagendador.multicom.model.response.MessageResponse;
import com.proyectoagendador.multicom.repository.UserRepository;
import com.proyectoagendador.multicom.security.TokenProviderSecurity;
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
    private RoleService roleService;

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
        when(this.userRepository.existsByEmailOrNumberPhone(request.getEmail() ,request.getNumber())).thenReturn(true);
        assertEquals(this.service.registerForCustomer(this.request), new MessageResponse(GeneralConstants.DATA_REPEATED));
    }
    @Test
    void registerForDocumentCustomerExisting() {
        when(this.userRepository.existsByDocumentTypeAndDocumentNumber(request.getDocumentNumber() ,request.getDocument())).thenReturn(true);
        assertEquals(this.service.registerForCustomer(this.request), new MessageResponse(GeneralConstants.DATA_REPEATED));
    }

    @Test
    void registerForCustomer() {
        Role role = new Role(RoleNameEnum.ROLE_CUSTOMER.name(), RoleNameEnum.ROLE_CUSTOMER.getValue());
        when(this.userRepository.existsByEmailOrNumberPhone(request.getEmail() ,request.getNumber())).thenReturn(false);
        when(this.roleService.findByName(RoleNameEnum.ROLE_CUSTOMER.name())).thenReturn(role);
        assertEquals(this.service.registerForCustomer(this.request), new MessageResponse(GeneralConstants.REGISTER_AUTH));
    }

    @Test
    void login() {
        User user = new User();
        user.setEmail(this.request.getEmail());
        when(this.userRepository.findByEmail(request.getEmail())).thenReturn(Optional.of(user));
        assertNotNull(this.service.login(this.authRequest));
    }

    @Test
    void validatedToken() {
        assertNotNull(this.service.validatedToken(""));
    }

    @Test
    void loginUserNotFound() {
        when(this.userRepository.findByEmail(request.getEmail())).thenReturn(Optional.empty());
        Throwable throwable = assertThrows(Throwable.class, () -> this.service.login(this.authRequest));
        assertEquals(BusinessException.class, throwable.getClass());
    }

    @Test
    void registerCustomerForUser() {
        this.request.setPassword(null);
        Role role = new Role(RoleNameEnum.ROLE_CUSTOMER.name(), RoleNameEnum.ROLE_CUSTOMER.getValue());
        when(this.userRepository.existsByEmailOrNumberPhone(request.getEmail() ,request.getNumber())).thenReturn(false);
        when(this.roleService.findByName(RoleNameEnum.ROLE_CUSTOMER.name())).thenReturn(role);
        assertEquals(this.service.registerForCustomer(this.request), new MessageResponse(GeneralConstants.REGISTER_AUTH));
    }
}

