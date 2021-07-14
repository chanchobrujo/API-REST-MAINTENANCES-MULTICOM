package com.proyectoIntegrador.MultiCom.controller;

import com.proyectoIntegrador.MultiCom.dto.*;
import com.proyectoIntegrador.MultiCom.model.Cliente;  
import com.proyectoIntegrador.MultiCom.service.*; 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.*; 

@RestController
@RequestMapping("/client")
@CrossOrigin(origins = "*") 
public class ClientController {
    @Autowired
    ClientService clienteService;

    @PreAuthorize("hasRole('ROLE_MOD') or hasRole('ROLE_ADMIN') ")
    @GetMapping("") 
    public ResponseEntity<?> list(){ 
		List<Cliente> list = clienteService.list(); 
        return new ResponseEntity< List<Cliente> >(list, HttpStatus.OK);
    }  

    @PreAuthorize("hasRole('ROLE_MOD') or hasRole('ROLE_ADMIN') ")
    @GetMapping("/detail/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") int id){
        if(!clienteService.existsById(id))
            return new ResponseEntity<Object>(new _Message("Cliente no encontrado."), HttpStatus.UNAUTHORIZED);
        
        Cliente cliente = clienteService.getOne(id).get();
        return new ResponseEntity<Cliente>(cliente, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")                          
    @PostMapping("/create")                         
    public ResponseEntity<?> create(@RequestBody ClientDto clienteDto){                            
        try {                           

            Cliente cliente = new Cliente(clienteDto.getNombre(), clienteDto.getApellido(), clienteDto.getNumero(), clienteDto.getEmail());
            clienteService.save(cliente);
            return new ResponseEntity<Object>(new _Message("Cliente agregado."), HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<Object>(new _Message("Datos repetidos."), HttpStatus.BAD_REQUEST);
        } 
    }
    
    @PreAuthorize("hasRole('ROLE_ADMIN')")  
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id")int id){
        if(!clienteService.existsById(id))
            return new ResponseEntity<Object>(new _Message("Cliente no encontrado."), HttpStatus.NOT_FOUND);
            
        try {
        	
            clienteService.delete(id); 
            return new ResponseEntity<Object>(new _Message("Cliente eliminado."), HttpStatus.OK);
            
        } catch (Exception e) {
            return new ResponseEntity<Object>(new _Message("Cliente asignado a una cita."), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")  
    @PutMapping ("/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id")int id, @RequestBody ClientDto clienteDto){
        if(!clienteService.existsById(id))
            return new ResponseEntity<Object>(new _Message("Cliente no encontrado."), HttpStatus.NOT_FOUND);

        Cliente cliente = clienteService.getOne(id).get();

        cliente.setNombre(clienteDto.getNombre());
        cliente.setApellido(clienteDto.getApellido());
        cliente.setNumero(clienteDto.getNumero());
        cliente.setEmail(clienteDto.getEmail());

        try {
        	
            clienteService.save(cliente);
            return new ResponseEntity<Object>(new _Message("Cliente actualizado."), HttpStatus.OK);
            
        } catch (Exception e) {
            return new ResponseEntity<Object>(new _Message("Datos repetidos."), HttpStatus.BAD_REQUEST);
        }
    }

}