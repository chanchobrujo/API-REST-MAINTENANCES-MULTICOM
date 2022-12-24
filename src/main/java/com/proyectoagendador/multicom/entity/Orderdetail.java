package com.proyectoagendador.multicom.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "orderdetail")
public
class Orderdetail {
    @Id
    @Size(max = 12)
    @Column(name = "id", nullable = false, length = 12)
    private String id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "appointmentId", nullable = false)
    private Appointment appointment;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "productId", nullable = false)
    private Product product;

    @NotNull
    @Column(name = "actualPrice", nullable = false, precision = 4, scale = 2)
    private BigDecimal actualPrice;

    @NotNull
    @Column(name = "subtotal", nullable = false, precision = 4, scale = 2)
    private BigDecimal subtotal;

    @NotNull
    @Lob
    @Column(name = "detail", nullable = false)
    private String detail;

    @NotNull
    @Column(name = "quantity", nullable = false)
    private Integer quantity;
}