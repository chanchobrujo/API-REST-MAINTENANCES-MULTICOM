package com.proyectoagendador.multicom.repository;

import com.proyectoagendador.multicom.entity.Category;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public
interface CategoryRepository extends JpaRepository<Category, Integer> {
    boolean existsByName (String name);

    Optional<Category> findByName (String name);

    @Query(value  = "SELECT * FROM category ORDER BY created_date DESC", nativeQuery = true)
    Page<Category> retrieveAllCategories(Pageable page);
}