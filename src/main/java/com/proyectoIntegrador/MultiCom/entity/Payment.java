package com.proyectoIntegrador.MultiCom.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.Instant;

@Data
@Entity
@Table(name = "payment")
public
class Payment {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "appointmentId", nullable = false)
    private Appointment appointment;

    @NotNull
    @Column(name = "total", nullable = false, precision = 5, scale = 2)
    private BigDecimal total;

    @Size(max = 255)
    @NotNull
    @Column(name = "description", nullable = false)
    private String description;

    @NotNull
    @Column(name = "createdDate", nullable = false)
    private Instant createdDate;
}