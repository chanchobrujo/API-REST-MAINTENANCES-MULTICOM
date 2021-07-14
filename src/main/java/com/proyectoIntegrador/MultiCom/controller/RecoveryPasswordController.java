package com.proyectoIntegrador.MultiCom.controller;

import org.springframework.web.bind.annotation.*;
import com.proyectoIntegrador.MultiCom.dto.*;
import com.proyectoIntegrador.MultiCom.model.*; 
import com.proyectoIntegrador.MultiCom.jwt.*;
import com.proyectoIntegrador.MultiCom.logic.myFuntions;
import com.proyectoIntegrador.MultiCom.service.*; 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*; 
import org.springframework.security.authentication.*;  
import org.springframework.security.crypto.password.PasswordEncoder;  

@RestController
@RequestMapping("/recovery")
@CrossOrigin

public class RecoveryPasswordController {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserService usuarioService;

    @Autowired
    RoleService rolService;

    @Autowired
    JwtProvider jwtProvider;  

    @PostMapping("/password")
    public ResponseEntity<?> recoverypassword(@RequestBody _RecoveryPassword recoveryFrom){
    	String msg ;
    	
        if(!usuarioService.existsByEmail( recoveryFrom.getEmail() )) 
        	return new ResponseEntity<Object>(new _Message("Correo no registrado."), HttpStatus.NOT_FOUND); 
        
        try {
        	
        	String newPassword = myFuntions.generatedID();
        	
        	Usuario user = usuarioService.getByEmail( recoveryFrom.getEmail() ).get();
        	user.setContrasena( passwordEncoder.encode( newPassword ) ); 
        	
        	usuarioService.save( user );  
        	
        	msg = myFuntions.sendMail( recoveryFrom.getEmail() , "Se cambiò su contraseña correctamente.", newPassword );
        	return new ResponseEntity<Object>(new _Message("Se reestableció su contraseña."), HttpStatus.OK);
		} catch (Exception e) {
			msg = e.getMessage();
        	return new ResponseEntity<Object>(new _Message(msg), HttpStatus.BAD_REQUEST);  
		} 
    } 
	
}

