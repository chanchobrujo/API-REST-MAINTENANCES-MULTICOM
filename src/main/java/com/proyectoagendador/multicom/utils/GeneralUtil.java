package com.proyectoagendador.multicom.utils;

import com.google.gson.Gson;
import com.proyectoagendador.multicom.exception.BusinessException;

import static java.util.Objects.isNull;

public
class GeneralUtil {
    private GeneralUtil() { }
    public static <D> D mapper(Class<D> destiny, Object origen) {
        Gson gson = new Gson();

        if (isNull(origen)) throw new BusinessException("Error en el formato");
        else return gson.fromJson(gson.toJson(origen), destiny);
    }
}
