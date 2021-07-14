package com.proyectoIntegrador.MultiCom.controller;

import com.proyectoIntegrador.MultiCom.dto.*; 
import com.proyectoIntegrador.MultiCom.logic.myFuntions;
import com.proyectoIntegrador.MultiCom.model.*;
import com.proyectoIntegrador.MultiCom.service.UserService; 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication; 
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*; 

@RestController
@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MOD') or hasRole('ROLE_CLIENTE')")
@RequestMapping("/Update")
@CrossOrigin(origins = "*")
public class updateMyData {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    PasswordEncoder passwordEncoder;
    
    @Autowired
    UserService userService;
    
    String id;
    
    @PostMapping("/Password/{id}")
    public ResponseEntity<?> updatePassword(@PathVariable("id") int id, @RequestBody _UpPassword uppassword){  
		if (! userService.existsById(id) ) return new ResponseEntity< Object >(new _Message("Este usuario no existe."), HttpStatus.NOT_FOUND);
		Usuario user = userService.getById(id).get(); 
		
		try {
			Authentication authentication = authenticationManager.authenticate( new UsernamePasswordAuthenticationToken(user.getEmail() , uppassword.getOldPassword())); 
			System.out.println(authentication.getName());
			
			if ( uppassword.getOldPassword().equals(uppassword.getNewPassword()) || uppassword.getOldPassword().equals(uppassword.getConfirmPassword()) )
				return new ResponseEntity< Object >(new _Message("La nueva contraseña debe ser diferente a la antigua."), HttpStatus.BAD_REQUEST);
			
			if (! uppassword.getNewPassword().equals(uppassword.getConfirmPassword()) )
				return new ResponseEntity< Object >(new _Message("Confirme correctamente la contraseña."), HttpStatus.BAD_REQUEST);
			
			myFuntions.sendMail( user.getEmail() , "Cambio de contraseña exitoso.", "Su contraseña a sido actualziada.");
			user.setContrasena( passwordEncoder.encode(uppassword.getNewPassword()) );
			userService.save(user);
			return new ResponseEntity< Object >(new _Message("La contraseña a sido actualizada."), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity< Object >(new _Message("Contraseña incorrecta."), HttpStatus.BAD_REQUEST);
		} 
		
    }  
}
