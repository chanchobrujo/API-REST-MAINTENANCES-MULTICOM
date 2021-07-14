package com.proyectoIntegrador.MultiCom.repository;

import com.proyectoIntegrador.MultiCom.model.Usuario;   
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Usuario, Integer> {
    Optional<Usuario> findByEmail(String email);
    Optional<Usuario> findById(int id);
    boolean existsById(int id);
    boolean existsByEmail(String email);
    boolean existsByNumero(String numero);
    boolean existsByContrasena(String password);
}