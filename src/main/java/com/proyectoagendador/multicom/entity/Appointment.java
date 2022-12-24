package com.proyectoagendador.multicom.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.Instant;

@Data
@Entity
@Table(name = "appointment")
public
class Appointment {
    @Id
    @Size(max = 12)
    @Column(name = "id", nullable = false, length = 12)
    private String id;

    @NotNull
    @Column(name = "total", nullable = false, precision = 5, scale = 2)
    private BigDecimal total;

    @NotNull
    @Column(name = "state", nullable = false)
    private Boolean state;

    @Size(max = 6)
    @Column(name = "customerId", length = 6)
    private String customerId;

    @Size(max = 6)
    @NotNull
    @Column(name = "userRegister", nullable = false, length = 6)
    private String userRegister;

    @NotNull
    @Column(name = "createdDate", nullable = false)
    private Instant createdDate;
}