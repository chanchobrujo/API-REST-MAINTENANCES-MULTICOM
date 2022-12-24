package com.proyectoagendador.multicom.controller.authentication;

import com.proyectoagendador.multicom.model.request.AuthRequest;
import com.proyectoagendador.multicom.model.request.SingUpRequest;
import com.proyectoagendador.multicom.model.response.MessageResponse;
import com.proyectoagendador.multicom.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.*;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth/")
public class AuthController {

	private final AuthService service;

	@PostMapping("register-customer")
	public ResponseEntity<MessageResponse> register(@Valid @RequestBody SingUpRequest request) {
		return ResponseEntity.ok(this.service.registerForCustomer(request));
	}

	@PostMapping("")
	public ResponseEntity<?> loginForUsers(@Valid @RequestBody AuthRequest request) {
		return ResponseEntity.ok(this.service.login(request));
	}

}
