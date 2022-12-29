package com.proyectoagendador.multicom.controller.authentication;

import com.proyectoagendador.multicom.common.constants.SecurityConstants;
import com.proyectoagendador.multicom.model.request.AuthRequest;
import com.proyectoagendador.multicom.model.request.SingUpRequest;
import com.proyectoagendador.multicom.model.response.DecryptTokenResponse;
import com.proyectoagendador.multicom.model.response.MessageResponse;
import com.proyectoagendador.multicom.model.response.TokenResponse;
import com.proyectoagendador.multicom.service.authentication.AuthService;
import com.proyectoagendador.multicom.utils.SecurityUtil;
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

	@PostMapping("verify-token")
	public ResponseEntity<DecryptTokenResponse> loginForUsers(@RequestHeader(name = SecurityConstants.AUTHORIZATION) String token) {
		return ResponseEntity.ok(this.service.validatedToken(token));
	}

}
