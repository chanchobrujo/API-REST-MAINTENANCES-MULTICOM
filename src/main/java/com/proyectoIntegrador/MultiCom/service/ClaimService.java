package com.proyectoIntegrador.MultiCom.service;

import com.proyectoIntegrador.MultiCom.model.*;
import com.proyectoIntegrador.MultiCom.repository.*; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*; 

@Service
@Transactional 
public class ClaimService {

    @Autowired
    ClaimRepository claimrepository;

    public List<Reclamo> list(){
        return claimrepository.findAll();
    }

    public void save(Reclamo reclamo){
    	claimrepository.save(reclamo);
    }

    public void delete(int id){
    	claimrepository.deleteById(id);
    }
}
