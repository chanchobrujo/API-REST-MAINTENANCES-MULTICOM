package com.proyectoIntegrador.MultiCom.service;

import com.proyectoIntegrador.MultiCom.model.Cliente; 
import com.proyectoIntegrador.MultiCom.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ClientService {

    @Autowired
    ClientRepository ClienteRepository;

    public List<Cliente> list(){
        return ClienteRepository.findAll();
    }

    public Optional<Cliente> getOne(int id){
        return ClienteRepository.findById(id);
    }

    public Optional<Cliente> getByNombre(String nombre){
        return ClienteRepository.findByNombre(nombre);
    } 

    public boolean existsById(int id){
        return ClienteRepository.existsById(id);
    }

    public boolean existsByNombre(String nombre){
        return ClienteRepository.existsByNombre(nombre);
    } 

    public void save(Cliente Cliente){
        ClienteRepository.save(Cliente);
    }

    public void delete(int id){
        ClienteRepository.deleteById(id);
    }

}