package com.proyectoagendador.multicom.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Instant;
import java.util.UUID;

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
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idRole", nullable = false)
    private Role idRole;

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
    private Instant createdDate;

    public User(String firstName, String lastName, String numberPhone, String doc, String numberDoc, String email, Role role, String password) {
        this.documentType = doc;
        this.documentNumber = numberDoc;
        this.id = (String) UUID.randomUUID().toString().toUpperCase().subSequence(0, 6);
        this.firstName = firstName;
        this.lastName = lastName;
        this.idRole = role;
        this.numberPhone = numberPhone;
        this.email = email;
        this.password = password;
        this.state = true;
        this.createdDate = Instant.now();
    }

}