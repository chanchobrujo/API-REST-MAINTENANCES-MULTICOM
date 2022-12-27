package com.proyectoagendador.multicom.repository;

import com.proyectoagendador.multicom.entity.Category;
import com.proyectoagendador.multicom.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.Set;

public
interface ProductRepository extends JpaRepository<Product, String> {
    boolean existsByName (String name);

    Optional<Product> findByName (String name);

    Set<Product> findByCategory_Id (Integer id);

    @Query(value  = "SELECT * FROM product ORDER BY created_date DESC", nativeQuery = true)
    Page<Product> retrieveAllProducts(Pageable page);

}