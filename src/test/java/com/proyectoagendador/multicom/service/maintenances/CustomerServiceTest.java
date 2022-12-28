package com.proyectoagendador.multicom.service.maintenances;

import org.junit.runner.RunWith;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.proyectoagendador.multicom.repository.UserRepository;
import com.proyectoagendador.multicom.model.request.SingUpRequest;
import com.proyectoagendador.multicom.model.response.MessageResponse;
import com.proyectoagendador.multicom.service.authentication.AuthService;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.data.domain.Sort.by;
import static org.springframework.data.domain.PageRequest.of;
import static com.proyectoagendador.multicom.common.enums.RoleNameEnum.ROLE_CUSTOMER;

@RunWith(MockitoJUnitRunner.class)
class CustomerServiceTest {

    private final static Pageable page = of(0, 1, by("createdDate").descending());

    @Mock
    private UserRepository repository;

    @Mock
    private AuthService authService;

    @InjectMocks
    private CustomerService service;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void save() {
        when(this.authService.registerForCustomer(new SingUpRequest())).thenReturn(new MessageResponse(""));
        assertNotNull(this.service.save(new SingUpRequest()));
    }

    @Test
    void findAll() {
        when(this.repository.findByRole_Name(ROLE_CUSTOMER.name(), page)).thenReturn(Page.empty(page));
        assertTrue(this.service.findAll(page).isEmpty());
    }
}
