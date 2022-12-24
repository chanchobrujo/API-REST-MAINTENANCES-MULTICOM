package com.proyectoagendador.multicom.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import static javax.persistence.GenerationType.IDENTITY;

@Data
@Entity
@Table(name = "role")
@NoArgsConstructor
public
class Role {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 20)
    @NotNull
    @Column(name = "name", nullable = false, unique = true, length = 20)
    private String name;

    @Size(max = 20)
    @NotNull
    @Column(name = "displayName", nullable = false, unique = true, length = 20)
    private String displayName;

    public Role(String name, String displayName) {
        this.name = name;
        this.displayName = displayName;
    }
}