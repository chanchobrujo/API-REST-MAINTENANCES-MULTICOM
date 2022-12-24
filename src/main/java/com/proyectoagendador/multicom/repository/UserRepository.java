package com.proyectoagendador.multicom.repository;

import com.proyectoagendador.multicom.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail (String email);
    Optional<User> findByEmailOrNumberPhone (String email, String numberPhone);
    Optional<User> findByDocumentNumberAndDocumentType (String documentNumber, String documentType);
}