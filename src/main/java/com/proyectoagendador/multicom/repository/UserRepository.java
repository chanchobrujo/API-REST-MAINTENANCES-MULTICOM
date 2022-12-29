package com.proyectoagendador.multicom.repository;

import com.proyectoagendador.multicom.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Page<User> findByRole_Name (String name, Pageable pageable);
    Optional<User> findByEmail (String email);
    boolean existsByEmailOrNumberPhone (String email, String numberPhone);
    boolean existsByDocumentTypeAndDocumentNumber (String documentType, String documentNumber);
}