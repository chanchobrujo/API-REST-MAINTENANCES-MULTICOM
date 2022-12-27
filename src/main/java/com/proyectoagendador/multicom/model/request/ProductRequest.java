package com.proyectoagendador.multicom.model.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public
class ProductRequest {
    @NotEmpty
    private String name;
    private double cost;
    @NotEmpty
    private String description;
    private Integer categoryId;
}
