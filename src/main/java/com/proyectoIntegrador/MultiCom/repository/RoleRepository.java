package com.proyectoIntegrador.MultiCom.repository;

import com.proyectoIntegrador.MultiCom.model.Rol; 
import com.proyectoIntegrador.MultiCom.enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Rol, Integer> {
    Optional<Rol> findByRolNombre(RoleName rolNombre);
}