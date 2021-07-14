package com.proyectoIntegrador.MultiCom.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*; 

@RestController
@RequestMapping("/")
@CrossOrigin(origins = "*")
public class infoController {
	@GetMapping("")
	public ResponseEntity<?> info(){
		return new ResponseEntity<String>("API Rest MULTICOM, protected data.", HttpStatus.ACCEPTED);
	}
}
