package com.proyectoIntegrador.MultiCom.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.GrantedAuthority;
import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenResponse {
    private String token;
}