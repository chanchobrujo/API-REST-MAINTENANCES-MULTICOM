package com.proyectoagendador.multicom.model.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import javax.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SingUpRequest {
	@NotNull
    private String name;
    @NotNull
    private String surname;
    @NotNull
    private String document;
    @NotNull
    private String documentNumber;
	@NotNull
    @Min(value = 15)
    private String number;
	@Email
	@NotNull
    private String email;
    private String password;
}