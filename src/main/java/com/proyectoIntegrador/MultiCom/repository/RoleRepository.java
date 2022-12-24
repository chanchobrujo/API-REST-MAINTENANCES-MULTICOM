package com.proyectoIntegrador.MultiCom.repository;

import com.proyectoIntegrador.MultiCom.entity.Role;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(String name);
}