package com.proyectoagendador.multicom.model.response;

import lombok.Data;

import java.math.BigDecimal;

@Data
public
class ProductResponse {
    private String id;
    private String name;
    private String description;
    private BigDecimal price;
    private String stateName;
    private String categoryName;
}
