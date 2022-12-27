package com.proyectoagendador.multicom.model.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public
class CategoryRequest {
    @NotEmpty
    private String name;
    @NotEmpty
    private String description;
}
