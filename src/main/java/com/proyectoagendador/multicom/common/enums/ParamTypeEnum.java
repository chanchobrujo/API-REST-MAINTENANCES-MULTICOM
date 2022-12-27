package com.proyectoagendador.multicom.common.enums;

import com.proyectoagendador.multicom.common.constants.GeneralConstants;
import com.proyectoagendador.multicom.exception.BusinessException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import static java.util.stream.Stream.of;

@Getter
@ToString
@AllArgsConstructor
public
enum ParamTypeEnum {

    NAME("name"),
    ID("id");

    private final String value;

    public static ParamTypeEnum findByValue(String value) {
        return of(values())
                .filter(rol -> rol.value.equals(value))
                .findFirst()
                .orElseThrow(() -> new BusinessException(GeneralConstants.GENERIC_CODE));
    }
}
