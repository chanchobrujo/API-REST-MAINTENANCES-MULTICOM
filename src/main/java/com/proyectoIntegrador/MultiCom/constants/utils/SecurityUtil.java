package com.proyectoIntegrador.MultiCom.constants.utils;

import org.json.JSONException;
import org.json.JSONObject;

public
class SecurityUtil {

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
}
