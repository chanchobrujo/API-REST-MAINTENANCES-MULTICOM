package com.proyectoagendador.multicom.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static com.proyectoagendador.multicom.utils.GeneralUtil.generatedId;

@Data
@Entity
@Table(name = "product")
@NoArgsConstructor
public
class Product {
    @Id
    @Column(name = "id", unique = true, nullable = false, length = 4)
    private String id;

    @Column(name = "name", unique = true, nullable = false, length = 25)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "category", nullable = false)
    private Category category;

    @Column(name = "state", nullable = false)
    private Boolean state;

    @Column(name = "createdDate", nullable = false)
    private LocalDateTime createdDate;

    public Product(String name, String description, double price, Category category){
        this.id = generatedId(4);
        this.price = BigDecimal.valueOf(price);
        this.state = true;
        this.name = name;
        this.description = description;
        this.category = category;
        this.createdDate = LocalDateTime.now();
    }
}