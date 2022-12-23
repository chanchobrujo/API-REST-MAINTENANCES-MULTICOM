package com.proyectoIntegrador.MultiCom.model.request;

import lombok.Data;

import javax.validation.constraints.*;

@Data
public class AuthRequest {
	@Email
	@NotNull
    private String email; 
	@NotNull
    private String password;
}