package com.proyectoagendador.multicom.mapper;

import com.proyectoagendador.multicom.entity.Role;
import com.proyectoagendador.multicom.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(MockitoJUnitRunner.class)
class UserMapperTest {

    private final static User user = new User("", "", "","", "", "", new Role("ROL", "ROL"), "xfg");

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void test() {
        assertEquals(2, UserMapper.buildResponse(user).getDocument().size());
        assertEquals(user.getEmail(), UserMapper.buildDetailAuth(user).getUsername());
    }
}
