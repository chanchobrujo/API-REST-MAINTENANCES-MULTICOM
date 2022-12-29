package com.proyectoagendador.multicom.service.maintenances;

import java.util.Optional;

import org.junit.runner.RunWith;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.proyectoagendador.multicom.entity.Product;
import com.proyectoagendador.multicom.entity.Category;
import com.proyectoagendador.multicom.exception.BusinessException;
import com.proyectoagendador.multicom.model.request.ProductRequest;
import com.proyectoagendador.multicom.repository.ProductRepository;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.data.domain.Sort.by;
import static org.springframework.data.domain.PageRequest.of;

@RunWith(MockitoJUnitRunner.class)
class ProductServiceTest {

    private final static Pageable page = of(0, 1, by("createdDate").descending());
    private final static ProductRequest request = new ProductRequest();
    private final static Product product = new Product();
    private final static Product product2 = new Product();
    private final static Category category = new Category();

    @Mock
    private CategoryService categoryService;

    @Mock
    private ProductRepository repository;

    @InjectMocks
    private ProductService service;

    @BeforeEach
    void init() {
        category.setId(1);
        category.setName("");
        category.setDescription("");

        product2.setId("ID2");
        product2.setName("");
        product2.setState(true);

        product.setId("ID");
        product.setName("");
        product.setState(true);
        product.setCategory(category);

        request.setName("");
        request.setCategoryId(1);
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void update() {
        when(this.repository.findById("ID")).thenReturn(Optional.of(product));
        when(this.repository.findByName("")).thenReturn(Optional.of(product2));
        Throwable throwable = assertThrows(Throwable.class, () -> this.service.update("ID", request));
        assertEquals(BusinessException.class, throwable.getClass());

        when(this.repository.findById("ID")).thenReturn(Optional.of(product));
        when(this.repository.findByName("")).thenReturn(Optional.empty());
        assertNotNull(this.service.update("ID", request));

        request.setCategoryId(666);
        when(this.categoryService.findById(666)).thenReturn(new Category());
        when(this.repository.findById("ID")).thenReturn(Optional.of(product));
        when(this.repository.findByName("")).thenReturn(Optional.of(product));
        assertNotNull(this.service.update("ID", request));
    }

    @Test
    void register() {
        when(this.repository.existsByName("")).thenReturn(true);
        Throwable throwable = assertThrows(Throwable.class, () -> this.service.register(request));
        assertEquals(BusinessException.class, throwable.getClass());

        when(this.repository.existsByName("")).thenReturn(false);
        when(this.categoryService.findById(1)).thenReturn(new Category());
        assertNotNull(this.service.register(request));
    }

    @Test
    void retrieveAll() {
        when(this.repository.retrieveAllProducts(page)).thenReturn(Page.empty(page));
        assertTrue(this.service.retrieveAll(page).isEmpty());
    }

    @Test
    void findById() {
        when(this.repository.findById("ID")).thenReturn(Optional.of(product));
        assertNotNull(this.service.findById("ID"));

        when(this.repository.findById("")).thenReturn(Optional.empty());
        Throwable throwable = assertThrows(Throwable.class, () -> this.service.findById(""));
        assertEquals(BusinessException.class, throwable.getClass());
    }
}
