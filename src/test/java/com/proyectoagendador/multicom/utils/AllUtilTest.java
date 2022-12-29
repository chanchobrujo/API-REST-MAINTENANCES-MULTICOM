package com.proyectoagendador.multicom.utils;

import com.proyectoagendador.multicom.entity.Category;
import com.proyectoagendador.multicom.exception.BusinessException;
import com.proyectoagendador.multicom.model.response.CategoryResponse;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(MockitoJUnitRunner.class)
class AllUtilTest {

    private final static Category category = new Category();
    private final static JSONObject json = new JSONObject();

    @BeforeEach
    void init() {
        json.put("error", "error");
        json.put("message", "Acceso denegado");

        category.setId(1);
        category.setDescription("");
        category.setName("");
        category.setCreatedDate(LocalDateTime.now());

        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testDate() {
        assertNotNull(DateUtil.formatterDate(LocalDateTime.now()));
    }

    @Test
    void testGeneral() {
        Throwable throwable =  assertThrows(BusinessException.class, () -> GeneralUtil.mapper(CategoryResponse.class, null));
        assertEquals(BusinessException.class, throwable.getClass());
        assertNotNull(GeneralUtil.mapper(CategoryResponse.class, category));
        assertEquals(3, GeneralUtil.generatedId(3).length());
    }

    @Test
    void testSecurity() {
        assertEquals("d", SecurityUtil.getToken("Bearer d"));
        assertEquals("", SecurityUtil.getToken(""));
        assertEquals("", SecurityUtil.getToken("GAAA"));
        assertEquals(json.toString(), SecurityUtil.retrieveData("error"));
    }
}
