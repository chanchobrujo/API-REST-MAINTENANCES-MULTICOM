package com.proyectoIntegrador.MultiCom.dto;

import javax.validation.constraints.*;
public class _LoginUser{
	@Email
	@NotNull
    private String email; 
	@NotNull
    private String password; 

    public _LoginUser(){
        
    }

    public _LoginUser(String email, String password){
        this.email = email;
        this.password = password;
    }
    
    public String getEmail() {
        return email;
    }
    public String getPassword() {
        return password;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}