package com.proyectoagendador.multicom.model.response;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionTypeResponse {
    private String error;
    private String message;
}