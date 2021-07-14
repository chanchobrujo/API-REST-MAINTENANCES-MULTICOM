package com.proyectoIntegrador.MultiCom.service;

import com.proyectoIntegrador.MultiCom.model.Rol; 
import com.proyectoIntegrador.MultiCom.enums.RoleName;
import com.proyectoIntegrador.MultiCom.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class RoleService {

    @Autowired
    RoleRepository RolRepository;

    public Optional<Rol> getByRolNombre(RoleName rolNombre){
        return RolRepository.findByRolNombre(rolNombre);
    }
    
    public int getRolesSize() {
    	return RolRepository.findAll().size();
    }

    public void save(Rol rol){
        RolRepository.save(rol);
    }
}