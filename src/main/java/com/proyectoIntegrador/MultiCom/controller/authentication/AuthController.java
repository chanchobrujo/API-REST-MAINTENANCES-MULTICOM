package com.proyectoIntegrador.MultiCom.controller.authentication;

import com.proyectoIntegrador.MultiCom.model.request.AuthRequest;
import com.proyectoIntegrador.MultiCom.model.request.SingUpRequest;
import com.proyectoIntegrador.MultiCom.model.response.MessageResponse;
import com.proyectoIntegrador.MultiCom.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.*;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth/")
public class AuthController {

	private final AuthService service;

	@PostMapping("register")
	public ResponseEntity<MessageResponse> register(@Valid @RequestBody SingUpRequest request) {
		return ResponseEntity.ok(this.service.registerForCustomer(request));
	}

	@PostMapping("")
	public ResponseEntity<?> loginForUsers(@Valid @RequestBody AuthRequest request) {
		return ResponseEntity.ok(this.service.login(request));
	}

}
