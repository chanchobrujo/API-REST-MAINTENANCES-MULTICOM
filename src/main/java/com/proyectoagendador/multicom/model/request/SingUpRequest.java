package com.proyectoagendador.multicom.model.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import javax.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SingUpRequest {
    @NotEmpty
    private String name;
    @NotEmpty
    private String surname;
    @NotEmpty
    private String document;
    @NotEmpty
    private String documentNumber;
    @NotEmpty
    @Min(value = 15)
    private String number;
	@Email
    @NotEmpty
    private String email;
    private String password;
}