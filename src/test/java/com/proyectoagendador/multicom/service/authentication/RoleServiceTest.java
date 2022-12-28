package com.proyectoagendador.multicom.service.authentication;

import java.util.Optional;

import org.junit.runner.RunWith;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import com.proyectoagendador.multicom.entity.Role;
import com.proyectoagendador.multicom.repository.RoleRepository;
import com.proyectoagendador.multicom.exception.BusinessException;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
class RoleServiceTest {

    @Mock
    private RoleRepository repository;

    @InjectMocks
    private RoleService service;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findByName() {
        when(this.repository.findByName("")).thenReturn(Optional.of(new Role()));
        assertNotNull(this.service.findByName(""));

        when(this.repository.findByName("f")).thenReturn(Optional.empty());
        Throwable throwable = assertThrows(Throwable.class, () -> this.service.findByName("f"));
        assertEquals(BusinessException.class, throwable.getClass());
    }

}
