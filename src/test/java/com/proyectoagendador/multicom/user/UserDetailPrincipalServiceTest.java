package com.proyectoagendador.multicom.user;

import java.util.Optional;

import com.proyectoagendador.multicom.common.enums.RoleNameEnum;
import com.proyectoagendador.multicom.entity.Role;
import com.proyectoagendador.multicom.entity.User;
import com.proyectoagendador.multicom.exception.BusinessException;
import com.proyectoagendador.multicom.model.dto.UserPrincipalDto;
import com.proyectoagendador.multicom.repository.UserRepository;
import com.proyectoagendador.multicom.service.maintenences.user.UserDetailPrincipalService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static java.util.Collections.singleton;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(MockitoJUnitRunner.class)
class UserDetailPrincipalServiceTest {

    private final User user = new User();
    private UserPrincipalDto principal = new UserPrincipalDto();

    @Mock
    private UserRepository repository;

    @InjectMocks
    private UserDetailPrincipalService service;

    @BeforeEach
    void init() {
        user.setEmail("email");
        user.setPassword("password");
        user.setRole(new Role(RoleNameEnum.ROLE_MOD.name(), "ROL"));

        principal = new UserPrincipalDto(user.getEmail(), user.getPassword(), singleton(new SimpleGrantedAuthority(user.getRole().getName())));
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void loadUserByUsername() {
        when(this.repository.findByEmail(user.getEmail())).thenReturn(Optional.of(this.user));
        assertEquals(this.service.loadUserByUsername("email").getUsername(), this.principal.getUsername());
    }

    @Test
    void loadUserByUsernameNotFound() {
        when(this.repository.findByEmail(user.getEmail())).thenReturn(Optional.empty());
        Throwable throwable =  assertThrows(Throwable.class, () -> this.service.loadUserByUsername("email"));
        assertEquals(BusinessException.class, throwable.getClass());
    }

}
