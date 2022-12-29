package com.proyectoagendador.multicom.service.maintenances;

import java.util.Optional;
import java.time.LocalDateTime;

import org.junit.runner.RunWith;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.proyectoagendador.multicom.entity.Category;
import com.proyectoagendador.multicom.mapper.CategoryMapper;
import com.proyectoagendador.multicom.exception.BusinessException;
import com.proyectoagendador.multicom.repository.CategoryRepository;
import com.proyectoagendador.multicom.model.request.CategoryRequest;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.data.domain.PageRequest.of;

@RunWith(MockitoJUnitRunner.class)
class CategoryServiceTest {
    private final static Category category = new Category();
    private final static Category category2 = new Category();
    private final static Pageable page = of(0, 1);
    private final static CategoryRequest request = new CategoryRequest();

    @Mock
    private CategoryRepository repository;

    @InjectMocks
    private CategoryService service;

    @BeforeEach
    void init() {
        category2.setId(2);
        category2.setName("");
        category2.setDescription("");
        category2.setCreatedDate(LocalDateTime.now());

        category.setId(1);
        category.setName("");
        category.setDescription("");
        category.setCreatedDate(LocalDateTime.now());

        request.setName("");
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findById() {
        when(this.repository.findById(1)).thenReturn(Optional.of(category));
        assertNotNull(this.service.findById(1));

        when(this.repository.findById(2)).thenReturn(Optional.empty());
        Throwable throwable = assertThrows(Throwable.class, () -> this.service.findById(2));
        assertEquals(BusinessException.class, throwable.getClass());
    }

    @Test
    void update() {
        when(this.repository.findById(1)).thenReturn(Optional.of(category));
        when(this.repository.findByName("")).thenReturn(Optional.empty());
        assertNotNull(this.service.update("1", request));

        when(this.repository.findById(1)).thenReturn(Optional.of(category));
        when(this.repository.findByName("")).thenReturn(Optional.of(category2));
        Throwable throwable = assertThrows(Throwable.class, () -> this.service.update("1", request));
        assertEquals(BusinessException.class, throwable.getClass());

        when(this.repository.findById(1)).thenReturn(Optional.of(category));
        when(this.repository.findByName("")).thenReturn(Optional.of(category));
        assertNotNull(this.service.update("1", request));
    }

    @Test
    void register() {
        when(this.repository.existsByName("")).thenReturn(false);
        assertNotNull(this.service.register(request));

        when(this.repository.existsByName("")).thenReturn(true);
        Throwable throwable = assertThrows(Throwable.class, () -> this.service.register(request));
        assertEquals(BusinessException.class, throwable.getClass());
    }

    @Test
    void retrieveOne() {
        when(this.repository.findById(1)).thenReturn(Optional.of(category));
        assertEquals(CategoryMapper.mapperResponse(category), this.service.retrieveOne("id", "1"));

        when(this.repository.findByName("")).thenReturn(Optional.of(category));
        assertEquals(CategoryMapper.mapperResponse(category), this.service.retrieveOne("name", ""));

        when(this.repository.findByName("f")).thenReturn(Optional.empty());
        Throwable throwable = assertThrows(Throwable.class, () -> this.service.retrieveOne("name", "f"));
        assertEquals(BusinessException.class, throwable.getClass());
    }

    @Test
    void retrieveAll() {
        when(this.repository.retrieveAllCategories(page)).thenReturn(Page.empty(page));
        assertTrue(this.service.retrieveAll(page).isEmpty());
    }

}
