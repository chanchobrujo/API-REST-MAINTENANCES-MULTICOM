package com.proyectoIntegrador.MultiCom.dto;

public class _UpEmail {
	private String email;
	private String newemail;
	
	public _UpEmail(String email, String newemail) { 
		this.email = email;
		this.newemail = newemail;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNewemail() {
		return newemail;
	}

	public void setNewemail(String newemail) {
		this.newemail = newemail;
	} 
	
}
