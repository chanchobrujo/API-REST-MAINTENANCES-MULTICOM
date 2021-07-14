package com.proyectoIntegrador.MultiCom.model;

import com.proyectoIntegrador.MultiCom.enums.RoleName;
import javax.persistence.*; 

@Entity
public class Rol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; 

    @Enumerated(EnumType.STRING)
    private RoleName rolNombre;

    public Rol() {
    }

    public Rol(RoleName rolNombre) {
        this.rolNombre = rolNombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public RoleName getRolNombre() {
        return rolNombre;
    }

    public void setRolNombre(RoleName rolNombre) {
        this.rolNombre = rolNombre;
    }
}