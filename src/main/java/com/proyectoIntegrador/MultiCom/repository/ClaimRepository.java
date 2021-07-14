package com.proyectoIntegrador.MultiCom.repository;

import com.proyectoIntegrador.MultiCom.model.*;  
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface ClaimRepository extends JpaRepository<Reclamo, Integer> {
	
}
