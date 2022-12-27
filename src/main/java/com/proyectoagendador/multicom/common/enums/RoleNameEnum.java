package com.proyectoagendador.multicom.common.enums;

import lombok.Getter;
import lombok.ToString;
import lombok.AllArgsConstructor;

@Getter
@ToString
@AllArgsConstructor
public enum RoleNameEnum {
    ROLE_MOD("Moderador"),
    ROLE_ADMIN("Administrador"),
    ROLE_CUSTOMER("Cliente");

    private final String value;
}