package com.proyectoagendador.multicom.utils;

import org.json.JSONObject;

import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static com.proyectoagendador.multicom.common.constants.GeneralConstants.SPACE;
import static com.proyectoagendador.multicom.common.constants.SecurityConstants.BEARER;

public
class SecurityUtil {
    private SecurityUtil() { }

    public static final String contentType = "application/json";

    public static String retrieveData(String ex)   {
        try {
            JSONObject json = new JSONObject();
            json.put("error", ex);
            json.put("message", "Acceso denegado");
            return json.toString();
        } catch (JSONException exception) {
            throw new RuntimeException(exception);
        }
    }

    public static String getToken(String request){
        if(isNotBlank(request) && request.startsWith(BEARER)) return request.replace(BEARER.concat(SPACE), "");
        return "";
    }
}
