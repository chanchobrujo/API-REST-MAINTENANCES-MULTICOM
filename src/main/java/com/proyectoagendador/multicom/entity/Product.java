package com.proyectoagendador.multicom.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "product")
public
class Product {
    @Id
    @Size(max = 4)
    @Column(name = "id", nullable = false, length = 4)
    private String id;

    @Size(max = 255)
    @NotNull
    @Column(name = "description", nullable = false)
    private String description;

    @Size(max = 25)
    @NotNull
    @Column(name = "name", nullable = false, length = 25)
    private String name;

    @NotNull
    @Column(name = "price", nullable = false, precision = 4, scale = 2)
    private BigDecimal price;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "category", nullable = false)
    private Category category;

    @NotNull
    @Column(name = "state", nullable = false)
    private Boolean state = false;
}