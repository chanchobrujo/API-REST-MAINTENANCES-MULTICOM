package com.proyectoIntegrador.MultiCom.dto;

import javax.validation.constraints.*;
public class _RecoveryPassword {
	@Email
    private String email; 
	@NotBlank
    private String password; 
	@NotBlank
    private String confirmpassword;
	
	public _RecoveryPassword(@Email String email, @NotBlank String password, @NotBlank String confirmpassword) { 
		this.email = email;
		this.password = password;
		this.confirmpassword = confirmpassword;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmpassword() {
		return confirmpassword;
	}

	public void setConfirmpassword(String confirmpassword) {
		this.confirmpassword = confirmpassword;
	}  
	
}
