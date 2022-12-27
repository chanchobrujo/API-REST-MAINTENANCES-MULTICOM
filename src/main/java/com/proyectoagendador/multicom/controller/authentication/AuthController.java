package com.proyectoagendador.multicom.controller.authentication;

import com.proyectoagendador.multicom.model.request.AuthRequest;
import com.proyectoagendador.multicom.model.request.SingUpRequest;
import com.proyectoagendador.multicom.model.response.MessageResponse;
import com.proyectoagendador.multicom.model.response.TokenResponse;
import com.proyectoagendador.multicom.service.authentication.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.*;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/authentication/")
public class AuthController {

	private final AuthService service;

	@PostMapping("register-customer")
	public ResponseEntity<MessageResponse> register(@Valid @RequestBody SingUpRequest request) {
		return ResponseEntity.ok(this.service.registerForCustomer(request));
	}

	@PostMapping("")
	public ResponseEntity<TokenResponse> loginForUsers(@Valid @RequestBody AuthRequest request) {
		return ResponseEntity.ok(this.service.login(request));
	}

}
