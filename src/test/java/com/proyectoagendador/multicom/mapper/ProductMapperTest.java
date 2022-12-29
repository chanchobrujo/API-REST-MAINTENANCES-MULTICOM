package com.proyectoagendador.multicom.mapper;

import com.proyectoagendador.multicom.entity.Category;
import com.proyectoagendador.multicom.entity.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(MockitoJUnitRunner.class)
class ProductMapperTest {

    private final static Product product = new Product();

    @BeforeEach
    void init() {

        product.setCategory(new Category("CAT", ""));
        product.setState(true);

        MockitoAnnotations.openMocks(this);
    }

    @Test
    void test() {
        assertEquals("Activo", ProductMapper.mapperResponse(product).getStateName());
        product.setState(false);
        assertEquals("Inactivo", ProductMapper.mapperResponse(product).getStateName());
    }
}
