package com.proyectoIntegrador.MultiCom.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.*;

@Data
@AllArgsConstructor
public class AuthRequest {
	@Email
	@NotNull
    private String email; 
	@NotNull
    private String password;
}