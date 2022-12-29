package com.proyectoagendador.multicom.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.UUID;

import static com.proyectoagendador.multicom.utils.GeneralUtil.generatedId;

@Data
@Entity
@Table(name = "user")
@NoArgsConstructor
public
class User {
    @Id
    @Size(max = 6)
    @Column(name = "id", nullable = false, length = 6)
    private String id;

    @Size(max = 50)
    @NotNull
    @Column(name = "firtsName", nullable = false, length = 50)
    private String firstName;

    @Size(max = 4)
    @NotNull
    @Column(name = "documentType", nullable = false, length = 4)
    private String documentType;

    @Size(max = 8)
    @NotNull
    @Column(name = "documentNumber", nullable = false, length = 8)
    private String documentNumber;

    @Size(max = 50)
    @NotNull
    @Column(name = "lastName", nullable = false, length = 50)
    private String lastName;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "role", nullable = false)
    private Role role;

    @Size(max = 15)
    @NotNull
    @Column(name = "numberPhone", nullable = false, length = 15)
    private String numberPhone;

    @Size(max = 50)
    @NotNull
    @Column(name = "email", nullable = false, length = 50)
    private String email;

    @NotNull
    @Lob
    @Column(name = "password", nullable = false)
    private String password;

    @NotNull
    @Column(name = "state", nullable = false)
    private Boolean state;

    @NotNull
    @Column(name = "createdDate", nullable = false)
    private LocalDateTime createdDate;

    public User(String firstName, String lastName, String numberPhone, String doc, String numberDoc, String email, Role role, String password) {
        this.documentType = doc;
        this.documentNumber = numberDoc;
        this.id = generatedId(6);
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.numberPhone = numberPhone;
        this.email = email;
        this.password = password;
        this.state = true;
        this.createdDate = LocalDateTime.now();
    }

}