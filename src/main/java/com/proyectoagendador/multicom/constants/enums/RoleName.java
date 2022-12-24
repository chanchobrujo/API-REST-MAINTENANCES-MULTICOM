package com.proyectoagendador.multicom.constants.enums;

import com.proyectoagendador.multicom.exception.BusinessException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import static java.util.stream.Stream.of;

@Getter
@ToString
@AllArgsConstructor
public enum RoleName{
    ROLE_MOD("Moderador"),
    ROLE_ADMIN("Administrador"),
    ROLE_CUSTOMER("Cliente");

    private final String value;

    public static RoleName findByValue(String value) {
        return of(values())
                .filter(rol -> rol.value.equals(value))
                .findFirst()
                .orElseThrow(() -> new BusinessException(""));
    }
}