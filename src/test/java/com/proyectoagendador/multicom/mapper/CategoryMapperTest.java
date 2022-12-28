package com.proyectoagendador.multicom.mapper;

import com.proyectoagendador.multicom.entity.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(MockitoJUnitRunner.class)
class CategoryMapperTest {

    private final static Category category = new Category();

    @BeforeEach
    void init() {

        category.setId(1);
        category.setDescription("");
        category.setName("");
        category.setCreatedDate(LocalDateTime.now());

        MockitoAnnotations.openMocks(this);
    }

    @Test
    void test() {
        assertEquals(category.getName(), CategoryMapper.mapperResponse(category).getName());
    }

}
