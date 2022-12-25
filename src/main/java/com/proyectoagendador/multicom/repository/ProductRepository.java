package com.proyectoagendador.multicom.repository;

import com.proyectoagendador.multicom.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public
interface ProductRepository extends JpaRepository<Product, String> {}