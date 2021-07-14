package com.proyectoIntegrador.MultiCom.repository;

import com.proyectoIntegrador.MultiCom.model.Cliente;  
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Cliente, Integer> { 
    Optional<Cliente> findById(int id);
    Optional<Cliente> findByNombre(String nombre);
    boolean existsByNombre(String nombre); 
}