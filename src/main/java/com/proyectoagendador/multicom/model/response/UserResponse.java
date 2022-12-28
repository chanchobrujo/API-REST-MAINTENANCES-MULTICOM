package com.proyectoagendador.multicom.model.response;

import com.proyectoagendador.multicom.utils.DateUtil;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Data
public
class UserResponse {
    private String id;
    private String date;
    private String state;
    private String fullName;
    private Map<String, String> contact;
    private Map<String, String> document;

    public UserResponse(String id, LocalDateTime date, boolean state, String fullName) {
        this.id = id;
        this.fullName = fullName;
        this.state = state ? "Activo" : "Inactivo";
        this.date = DateUtil.formatterDate(date);
    }
}
