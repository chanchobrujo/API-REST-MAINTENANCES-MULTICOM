package com.proyectoIntegrador.MultiCom.controller;

import com.proyectoIntegrador.MultiCom.dto.*;
import com.proyectoIntegrador.MultiCom.enums.RoleName;
import com.proyectoIntegrador.MultiCom.logic.myFuntions;
import com.proyectoIntegrador.MultiCom.model.*;
import com.proyectoIntegrador.MultiCom.service.*; 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*; 

@RestController
@PreAuthorize("hasRole('ROLE_ADMIN')")
@RequestMapping("/Users")
@CrossOrigin(origins = "*")
public class UserController { 

    @Autowired
    PasswordEncoder passwordEncoder;
    
    @Autowired
    UserService userService;

    @Autowired
    RoleService rolService;
    
    @GetMapping("/Excep/{myId}") 
    public ResponseEntity<?> list(@PathVariable("myId") int myId){
		List<Usuario> list = userService.list(); 
    	List<Usuario> list2 = new ArrayList<>();
		
		for (int i = 0; i < list.size(); i++) {
			Usuario user = (Usuario) list.get(i);
			if ( user.getId() != myId ) list2.add(user);
		} 
		
        return new ResponseEntity< List<Usuario> >(list2, HttpStatus.OK);
    } 

    @PostMapping("/Create")
    public ResponseEntity<?> create(@RequestBody _NewUser usuarioNew){   
    	String newpassword = myFuntions.generatedID();
    	Set<Rol> roles = new HashSet<>(); 
    	roles.add( rolService.getByRolNombre( RoleName.ROLE_MOD ).get() );
    	
    	String msg; 
    	
    	if (userService.existsByEmail(usuarioNew.getEmail()))
    		return new ResponseEntity<Object>(new _Message("Este correo ya a sido registrado."), HttpStatus.BAD_REQUEST);
    	
    	try {
    		msg = myFuntions.sendMail( usuarioNew.getEmail() , "Bienvenido, se le a asignado una contraseña:", newpassword );
		} catch (Exception e) {
			msg = "Error al enviar mensaje, verifique el correo.";
			return new ResponseEntity<Object>(new _Message(msg), HttpStatus.NOT_FOUND);
		} 
    	
    	Usuario user = new Usuario(usuarioNew.getNombre(),usuarioNew.getApellido(),usuarioNew.getNumero(), usuarioNew.getEmail(), passwordEncoder.encode(newpassword) , true);
    	user.setRoles(roles);
    	
    	userService.save(user);
        return new ResponseEntity<Object>(new  _Message( "Usuario creado correctamente, "+msg), HttpStatus.OK);
    } 
    
    @PutMapping("/State/{id}")
    public ResponseEntity<?> stateUser(@PathVariable("id") int id){
    	Usuario user = null;
    	user = userService.getById(id).get();
        if(user != null) {
        	user.setEstado( !user.getEstado() );
        	
    		userService.save(user);
    		if(user.getEstado() == true)
    			return new ResponseEntity<Object>(new _Message("El usuario a sido dado de alta."), HttpStatus.OK);
    		
    		else
    			return new ResponseEntity<Object>(new _Message("El usuario a sido dado de baja."), HttpStatus.OK);
        }
        
        return new ResponseEntity<Object>(new _Message("Usuario no encontrado."), HttpStatus.NOT_FOUND);
    } 
}
